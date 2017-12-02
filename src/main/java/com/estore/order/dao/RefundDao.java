package com.estore.order.dao;

import org.springframework.data.repository.CrudRepository;

import com.estore.order.model.Refund;

public interface RefundDao extends CrudRepository<Refund, Long> {

}
