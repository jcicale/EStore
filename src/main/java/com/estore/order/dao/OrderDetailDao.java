package com.estore.order.dao;

import org.springframework.data.repository.CrudRepository;

import com.estore.order.model.OrderDetail;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Long> {

}
