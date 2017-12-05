package com.estore.customer.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.estore.customer.model.Customer;

public interface CustomerDao extends CrudRepository<Customer, Long> {

	List<Customer> list_Customer_by_userName(@Param("userName") String userName);

}
