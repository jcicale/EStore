package com.estore.order.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.EStoreConstants;
import com.estore.inventory.dao.InventoryDao;
import com.estore.order.dao.OrderDetailDao;
import com.estore.order.model.OrderDetail;
import com.estore.order.model.ShippingOrder;

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

	public boolean shipOrderDetail(ShippingOrder orderDetail, String trackingNumber) {
		if (orderDetail.getOrderState().equals(EStoreConstants.ORDER_STATUS_READY_TO_SHIP)) {
			orderDetail.setOrderState(EStoreConstants.ORDER_STATUS_SHIPPED);
			orderDetail.setTrackingNumber(trackingNumber);
			Calendar deliveryDate = Calendar.getInstance();
			deliveryDate.add(Calendar.DATE, 3);
			orderDetail.setEstimatedDelivery(deliveryDate);
			orderDetail = (ShippingOrder) save(orderDetail);
			return orderDetail != null;
		}
		return false;
	}

	public boolean orderDelivered(ShippingOrder orderDetail, Calendar delivered) {
		if (orderDetail.getOrderState().equals(EStoreConstants.ORDER_STATUS_SHIPPED)) {
			orderDetail.setOrderState(EStoreConstants.ORDER_STATUS_DELIVERED);
			orderDetail.setDelivered(delivered);
			orderDetail = (ShippingOrder) save(orderDetail);
			return orderDetail != null;
		}
		return false;
	}

}
