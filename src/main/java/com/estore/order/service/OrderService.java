package com.estore.order.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.EStoreConstants;
import com.estore.EStoreUtils;
import com.estore.SuperLink;
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
import com.estore.order.resource.OrderResource;

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
		for (Order order : list) {
			if (order.getOrderState().equals(EStoreConstants.ORDER_STATUS_PENDING)) {
				order.add(new SuperLink(
						linkTo(methodOn(OrderResource.class).acceptPayment(order.getOrderId())).withRel("accept"),
						"PUT"));
				order.add(new SuperLink(
						linkTo(methodOn(OrderResource.class).cancelOrder(order.getOrderId())).withRel("cancel"),
						"PUT"));
			} else {
				if (order.getOrderState().equals(EStoreConstants.ORDER_STATUS_VERIFIED)) {
					order.add(new SuperLink(
							linkTo(methodOn(OrderResource.class).fulfillOrder(order.getOrderId())).withRel("fulfill"),
							"PUT"));
				}
			}

		}
		return list;
	}

	public Iterable<Order> listAllByCustomerId(Long customerId) {
		Iterable<Order> list = orderDao.list_Orders_by_customerId(customerId);
		for (Order order : list) {
			if (order.getOrderState().equals(EStoreConstants.ORDER_STATUS_PENDING)) {
				order.add(new SuperLink(
						linkTo(methodOn(OrderResource.class).cancelOrder(order.getOrderId())).withRel("cancel"),
						"PUT"));
			}

		}
		return list;
	}

	public Iterable<Order> listAllByPartnerId(Long partnerId) {
		Iterable<Order> list = orderDao.list_Orders_by_partnerId(partnerId);
		for (Order order : list) {
			if (order.getOrderState().equals(EStoreConstants.ORDER_STATUS_COMPLETED)) {
				for (OrderDetail orderDetail : order.getOrderDetails()) {
					if (orderDetail.getOrderState().equals(EStoreConstants.ORDER_STATUS_READY_TO_SHIP)) {
						order.add(
								new SuperLink(
										linkTo(methodOn(OrderResource.class).shipOrderDetail(order.getOrderId(),
												orderDetail.getOrderDetailId(), "trackingNumber")).withRel("ship"),
										"PUT"));
						order.add(
								new SuperLink(linkTo(methodOn(OrderResource.class).cancelOrderDetail(order.getOrderId(),
										orderDetail.getOrderDetailId())).withRel("cancel"), "PUT"));
					} else {
						if (orderDetail.getOrderState().equals(EStoreConstants.ORDER_STATUS_SHIPPED)) {
							order.add(new SuperLink(
									linkTo(methodOn(OrderResource.class).orderDetailDelivered(order.getOrderId(),
											orderDetail.getOrderDetailId())).withRel("delivered"),
									"PUT"));
						}
					}
				}
			}
		}
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

	public Order acceptPayment(Order order) {
		if (order.getOrderState().equals(EStoreConstants.ORDER_STATUS_PENDING)) {
			if (order.getPaymentMethod() != null && order.getPaymentMethod().size() > 0) {
				for (PaymentMethod paymentMethod : order.getPaymentMethod()) {
					paymentMethod.setPaymentStatus(EStoreConstants.PAYMENT_STATUS_VERIFIED);
				}
			}
			order.setPaymentStatus(EStoreConstants.PAYMENT_STATUS_VERIFIED);
			order.setOrderState(EStoreConstants.ORDER_STATUS_VERIFIED);
			order = orderDao.save(order);
			order.add(new SuperLink(
					linkTo(methodOn(OrderResource.class).fulfillOrder(order.getOrderId())).withRel("fulfill"), "PUT"));
			return order;
		}
		return null;
	}

	public Order fulfillOrder(Order order) {
		if (order.getOrderState().equals(EStoreConstants.ORDER_STATUS_VERIFIED)) {
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

						} else {
							orderDetail.setOrderState(EStoreConstants.ORDER_STATUS_READY_TO_SHIP);
						}
						orderDetail.getInventory()
								.setQuantity(orderDetail.getInventory().getQuantity() - orderDetail.getQuantity());
					}
				}
			}
			order.setOrderState(EStoreConstants.ORDER_STATUS_COMPLETED);
			if (order.getPaymentMethod() != null && order.getPaymentMethod().size() > 0) {
				for (PaymentMethod paymentMethod : order.getPaymentMethod()) {
					paymentMethod.setPaymentStatus(EStoreConstants.PAYMENT_STATUS_PAID);
				}
			}
			order = orderDao.save(order);
			return order;
		}
		return null;
	}

	public Order cancelOrderDetail(Order order, Long orderDetailId) {
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
			return order;
		}
		return null;
	}

	public Order shipOrderDetail(Order order, Long orderDetailId, String trackingNumber) {
		ShippingOrder orderDetail = null;
		if (order.getOrderDetails() != null && order.getOrderDetails().size() > 0) {
			for (OrderDetail aux : order.getOrderDetails()) {
				if (orderDetail == null) {
					if (aux.getOrderDetailId().equals(orderDetailId)) {
						orderDetail = (ShippingOrder) aux;
					}
				}
			}
		}
		if (orderDetail != null && (orderDetail.getOrderState().equals(EStoreConstants.ORDER_STATUS_READY_TO_SHIP))) {
			orderDetail.setOrderState(EStoreConstants.ORDER_STATUS_SHIPPED);
			orderDetail.setTrackingNumber(trackingNumber);
			Calendar deliveryDate = Calendar.getInstance();
			deliveryDate.add(Calendar.DATE, 3);
			orderDetail.setEstimatedDelivery(deliveryDate);
			order = orderDao.save(order);
			order.add(new SuperLink(linkTo(methodOn(OrderResource.class).orderDetailDelivered(order.getOrderId(),
					orderDetail.getOrderDetailId())).withRel("delivered"), "PUT"));
			return order;
		}
		return null;
	}

	public Order orderDetailDelivered(Order order, Long orderDetailId) {
		ShippingOrder orderDetail = null;
		if (order.getOrderDetails() != null && order.getOrderDetails().size() > 0) {
			for (OrderDetail aux : order.getOrderDetails()) {
				if (orderDetail == null) {
					if (aux.getOrderDetailId().equals(orderDetailId)) {
						orderDetail = (ShippingOrder) aux;
					}
				}
			}
		}
		if (orderDetail.getOrderState().equals(EStoreConstants.ORDER_STATUS_SHIPPED)) {
			orderDetail.setOrderState(EStoreConstants.ORDER_STATUS_DELIVERED);
			orderDetail.setDelivered(Calendar.getInstance());
			order = orderDao.save(order);
			return order;
		}
		return null;
	}

	public Order cancelOrder(Order order) {
		if (order != null && order.getOrderState().equals(EStoreConstants.ORDER_STATUS_PENDING)) {
			order.setOrderState(EStoreConstants.ORDER_STATUS_CANCELED);
			if (order.getPaymentMethod() != null && order.getPaymentMethod().size() > 0) {
				for (OrderDetail aux : order.getOrderDetails()) {
					aux.setOrderState(EStoreConstants.ORDER_STATUS_CANCELED);
				}
				for (PaymentMethod paymentMethod : order.getPaymentMethod()) {
					Refund refund = new Refund(null, EStoreConstants.PAYMENT_STATUS_PENDING,
							paymentMethod.getSubTotal(), order.getOrderDetails().get(0), paymentMethod);
					refund = refundDao.save(refund);
					paymentMethod.setPaymentStatus(EStoreConstants.PAYMENT_STATUS_REFUNDED);
				}
			}
			order = orderDao.save(order);
			return order;
		}
		return null;
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
