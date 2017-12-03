package com.estore.order.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estore.order.model.Order;
import com.estore.order.model.ShippingOrder;
import com.estore.order.representation.OrderRequest;
import com.estore.order.service.OrderDetailService;
import com.estore.order.service.OrderService;

@RestController
@RequestMapping("order")
public class OrderResource {
	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@GetMapping
	public Iterable<Order> listAllOrders() {
		return orderService.listAllOrders();
	}

	@GetMapping("{orderId}")
	public Order getOrderById(@PathVariable("orderId") Long orderId) {
		return orderService.getOrderById(orderId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Order addOrder(@RequestBody OrderRequest orderRequest) {
		Order order = orderService.addOrder(orderService.toOrder(orderRequest));
		return order;
	}

	@RequestMapping(value = "{orderId}/accept", method = RequestMethod.PUT)
	public ResponseEntity<String> acceptPayment(@PathVariable("orderId") Long orderId) {
		if (orderService.acceptPayment(orderService.getOrderById(orderId))) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "{orderId}/fulfill", method = RequestMethod.PUT)
	public ResponseEntity<String> fulfillOrder(@PathVariable("orderId") Long orderId) {
		if (orderService.fulfillOrder(orderService.getOrderById(orderId))) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "{orderId}/orderDetail/{orderDetailId}/cancel", method = RequestMethod.PUT)
	public ResponseEntity<String> acceptPayment(@PathVariable("orderId") Long orderId,
			@PathVariable("orderDetailId") Long orderDetailId) {
		if (orderService.cancelOrderDetail(orderService.getOrderById(orderId), orderDetailId)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "{orderId}/orderDetail/{orderDetailId}/ship/{trackingNumber}", method = RequestMethod.PUT)
	public ResponseEntity<String> shipOrder(@PathVariable("orderId") Long orderId,
			@PathVariable("orderDetailId") Long orderDetailId, @PathVariable("trackingNumber") String trackingNumber) {
		if (orderDetailService.shipOrderDetail((ShippingOrder) orderDetailService.getById(orderDetailId),
				trackingNumber)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

}
