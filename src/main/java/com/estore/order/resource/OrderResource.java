package com.estore.order.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estore.order.model.Order;
import com.estore.order.service.OrderService;

@RestController
@RequestMapping("order")
public class OrderResource {
	@Autowired
	private OrderService orderService;

	@GetMapping
	public Iterable<Order> listAllOrders() {
		return orderService.listAllOrders();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Order addContact(@RequestBody Order order) {
		order = orderService.addOrder(order);
		return order;
	}

}
