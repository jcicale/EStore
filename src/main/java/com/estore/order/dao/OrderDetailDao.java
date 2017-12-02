package com.estore.order.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.estore.order.model.OrderDetail;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Long> {
	List<OrderDetail> list_OrderDetails_by_partnerId(@Param("partnerId") Long partnerId);

	List<OrderDetail> list_OrderDetails_by_partnerId_and_orderState(@Param("customerId") Long customerId,
			@Param("orderState") String orderState);

}
