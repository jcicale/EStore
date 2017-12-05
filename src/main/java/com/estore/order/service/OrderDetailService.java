package com.estore.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.inventory.dao.InventoryDao;
import com.estore.order.dao.OrderDetailDao;
import com.estore.order.model.OrderDetail;

@Service
public class OrderDetailService {
	@Autowired
	OrderDetailDao orderDetailDao;
	@Autowired
	InventoryDao inventoryDao;

	public OrderDetail save(OrderDetail orderDetail) {
		return orderDetailDao.save(orderDetail);
	}

	public Iterable<OrderDetail> listAll() {
		return orderDetailDao.findAll();
	}

	public OrderDetail getById(Long orderDetailId) {
		return orderDetailDao.findOne(orderDetailId);
	}

	public boolean remove(Long orderDetailId) {
		try {
			orderDetailDao.delete(orderDetailId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
