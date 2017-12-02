package com.estore.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.order.dao.OrderDao;
import com.estore.order.model.Order;

@Service
public class OrderService {
	
	@Autowired
	private OrderDao orderDao;

	public Iterable<Order> listAllOrders() {
		Iterable<Order> list = orderDao.findAll();
		/*for (Order order : list) {
			order.add(linkTo(InventoryResource.class).slash(inventory.getInventoryId()).withSelfRel());

		}*/
		return list;
	}
	
	public Order addOrder(Order order) {
		order = orderDao.save(order);
		return order;
	}
}
