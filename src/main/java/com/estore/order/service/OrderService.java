package com.estore.order.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.EStoreConstants;
import com.estore.EStoreUtils;
import com.estore.customer.service.AddressService;
import com.estore.customer.service.CustomerService;
import com.estore.inventory.service.InventoryService;
import com.estore.order.dao.OrderDao;
import com.estore.order.dao.RefundDao;
import com.estore.order.model.CreditCardPayment;
import com.estore.order.model.Order;
import com.estore.order.model.OrderDetail;
import com.estore.order.model.PayPalPayment;
import com.estore.order.model.PaymentMethod;
import com.estore.order.model.PickUpOrder;
import com.estore.order.model.Refund;
import com.estore.order.model.ShippingOrder;
import com.estore.order.representation.OrderDetailRequest;
import com.estore.order.representation.OrderRequest;
import com.estore.order.representation.PaymentMethodRequest;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private RefundDao refundDao;

	public Iterable<Order> listAllOrders() {
		Iterable<Order> list = orderDao.findAll();
		return list;
	}

	public Order getOrderById(Long orderId) {
		Order order = orderDao.findOne(orderId);
		return order;
	}

	public Order addOrder(Order order) {
		Double total = 0.0;
		if (order.getOrderDetails() != null && order.getOrderDetails().size() > 0) {
			for (OrderDetail orderDetail : order.getOrderDetails()) {
				System.out.println(orderDetail.getClass().toString());
				orderDetail.setOrderState(EStoreConstants.ORDER_STATUS_PENDING);
				total += orderDetail.getSubTotal();
			}
		}
		order.setTotal(total);
		Double totalPaid = 0.0;
		if (order.getPaymentMethod() != null && order.getPaymentMethod().size() > 0) {
			for (PaymentMethod paymentMethod : order.getPaymentMethod()) {
				paymentMethod.setPaymentStatus(EStoreConstants.PAYMENT_STATUS_PENDING);
				totalPaid += paymentMethod.getSubTotal();
			}
		}
		order.setOrderState(EStoreConstants.ORDER_STATUS_PENDING);
		order.setPaymentStatus(EStoreConstants.PAYMENT_STATUS_PENDING);
		if (totalPaid.equals(total)) {
			order = orderDao.save(order);
		}
		return order;
	}

	public boolean acceptPayment(Order order) {
		if (order.getOrderState().equals(EStoreConstants.ORDER_STATUS_PENDING)) {
			if (order.getPaymentMethod() != null && order.getPaymentMethod().size() > 0) {
				for (PaymentMethod paymentMethod : order.getPaymentMethod()) {
					paymentMethod.setPaymentStatus(EStoreConstants.PAYMENT_STATUS_VERIFIED);
				}
			}
			order.setPaymentStatus(EStoreConstants.PAYMENT_STATUS_VERIFIED);
			order.setOrderState(EStoreConstants.ORDER_STATUS_READY_TO_SHIP);
			order = orderDao.save(order);
			return order != null;
		}
		return false;
	}

	public boolean fulfillOrder(Order order) {
		if (order.getOrderState().equals(EStoreConstants.ORDER_STATUS_READY_TO_SHIP)) {
			if (order.getOrderDetails() != null && order.getOrderDetails().size() > 0) {
				for (OrderDetail orderDetail : order.getOrderDetails()) {
					if (orderDetail.getInventory().getQuantity() < orderDetail.getQuantity()) {
						cancelOrderDetail(order, orderDetail.getOrderDetailId());
					} else {
						if (orderDetail instanceof PickUpOrder) {
							((PickUpOrder) orderDetail).setTimeForPickUp(
									EStoreUtils.CalendarToString(Calendar.getInstance(), EStoreConstants.DATE_FORMAT)
											+ " 09:00 - 17:00");
							orderDetail.setOrderState(EStoreConstants.ORDER_STATUS_READY_TO_PICK_UP);
							orderDetail.getInventory()
									.setQuantity(orderDetail.getInventory().getQuantity() - orderDetail.getQuantity());

						} else {
							orderDetail.setOrderState(EStoreConstants.ORDER_STATUS_READY_TO_SHIP);
						}
					}
				}
			}
			order.setOrderState(EStoreConstants.ORDER_STATUS_FULFILLED);
			if (order.getPaymentMethod() != null && order.getPaymentMethod().size() > 0) {
				for (PaymentMethod paymentMethod : order.getPaymentMethod()) {
					paymentMethod.setPaymentStatus(EStoreConstants.PAYMENT_STATUS_PAID);
				}
			}
			order = orderDao.save(order);
			return order != null;
		}
		return false;
	}

	public boolean cancelOrderDetail(Order order, Long orderDetailId) {
		OrderDetail orderDetail = null;
		if (order.getOrderDetails() != null && order.getOrderDetails().size() > 0) {
			for (OrderDetail aux : order.getOrderDetails()) {
				if (orderDetail == null) {
					if (aux.getOrderDetailId().equals(orderDetailId)) {
						orderDetail = aux;
					}
				}
			}
		}
		if (orderDetail != null && (orderDetail.getOrderState().equals(EStoreConstants.ORDER_STATUS_READY_TO_SHIP)
				|| orderDetail.getOrderState().equals(EStoreConstants.ORDER_STATUS_READY_TO_PICK_UP))) {
			orderDetail.setOrderState(EStoreConstants.ORDER_STATUS_CANCELED);
			orderDetail.getInventory()
					.setQuantity(orderDetail.getInventory().getQuantity() + orderDetail.getQuantity());
			Double ammountRefund = orderDetail.getSubTotal();
			order.setTotal(order.getTotal() - ammountRefund);
			if (order.getPaymentMethod() != null && order.getPaymentMethod().size() > 0) {
				for (PaymentMethod paymentMethod : order.getPaymentMethod()) {
					if (ammountRefund > 0.0) {
						Double refunded = 0.0;
						if (paymentMethod.getSubTotal() > ammountRefund) {
							refunded = ammountRefund;
							ammountRefund = 0.0;
						} else {
							ammountRefund -= paymentMethod.getSubTotal();
							refunded = paymentMethod.getSubTotal();
						}
						Refund refund = new Refund(null, EStoreConstants.PAYMENT_STATUS_PENDING, refunded, orderDetail,
								paymentMethod);
						refund = refundDao.save(refund);
						if (paymentMethod.getSubTotal() == refunded) {
							paymentMethod.setPaymentStatus(EStoreConstants.PAYMENT_STATUS_REFUNDED);
						} else {
							paymentMethod.setPaymentStatus(EStoreConstants.PAYMENT_STATUS_PARTIALLY_REFUNDED);
						}
					}

				}
			}
			order = orderDao.save(order);
		}
		return order != null;
	}
	


	public Order toOrder(OrderRequest orderRequest) {
		List<OrderDetail> listOrderDetail = new ArrayList<>();
		Double total = 0.0;
		if (orderRequest.getOrderDetails() != null && orderRequest.getOrderDetails().size() > 0) {
			for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetails()) {
				OrderDetail aux = null;
				if (orderDetailRequest.getAddressId() != null) {
					aux = new ShippingOrder(null,
							inventoryService.getInventoryById(orderDetailRequest.getInventoryId()),
							orderDetailRequest.getQuantity(), EStoreConstants.ORDER_STATUS_PENDING,
							addressService.getAddressById(orderDetailRequest.getAddressId()), null);
				} else {
					aux = new PickUpOrder(null, inventoryService.getInventoryById(orderDetailRequest.getInventoryId()),
							orderDetailRequest.getQuantity(), EStoreConstants.ORDER_STATUS_PENDING);
				}

				listOrderDetail.add(aux);
				total += aux.getSubTotal();
			}
		}
		Double totalPaid = 0.0;
		List<PaymentMethod> listPaymentMethod = new ArrayList<>();
		if (orderRequest.getPaymentMethod() != null && orderRequest.getPaymentMethod().size() > 0) {
			for (PaymentMethodRequest paymentMethodRequest : orderRequest.getPaymentMethod()) {
				if (paymentMethodRequest.getTransactionId() != null) {
					listPaymentMethod.add(new PayPalPayment(null, EStoreConstants.PAYMENT_STATUS_PENDING,
							paymentMethodRequest.getSubTotal(), paymentMethodRequest.getTransactionId(),
							paymentMethodRequest.getAccountEmail()));
				} else {
					if (paymentMethodRequest.getCreditCardNumber() != null) {
						listPaymentMethod.add(new CreditCardPayment(null, EStoreConstants.PAYMENT_STATUS_PENDING,
								paymentMethodRequest.getSubTotal(), paymentMethodRequest.getCreditCardNumber(),
								paymentMethodRequest.getNameOnCard(), paymentMethodRequest.getSecurityCode(),
								paymentMethodRequest.getValidDate()));
					}
				}
				totalPaid += paymentMethodRequest.getSubTotal();
			}
		}
		Order order = new Order(null, listOrderDetail, EStoreConstants.PAYMENT_STATUS_PENDING,
				customerService.getCustomerById(orderRequest.getCustomerId()), EStoreConstants.ORDER_STATUS_PENDING,
				addressService.getAddressById(orderRequest.getBillingAddressId()), total, listPaymentMethod);
		return order;
	}
}
