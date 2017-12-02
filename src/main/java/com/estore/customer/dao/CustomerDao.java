package com.estore.customer.dao;

import org.springframework.data.repository.CrudRepository;

import com.estore.customer.model.Customer;

public interface CustomerDao extends CrudRepository<Customer, Long> {

}
