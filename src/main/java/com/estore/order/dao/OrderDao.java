package com.estore.order.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.estore.order.model.Order;

public interface OrderDao extends CrudRepository<Order, Long> {
	List<Order> list_Orders_by_customerId(@Param("customerId") Long customerId);

	List<Order> list_Orders_by_customerId_and_orderState(@Param("customerId") Long customerId,
			@Param("orderState") String orderState);

}
