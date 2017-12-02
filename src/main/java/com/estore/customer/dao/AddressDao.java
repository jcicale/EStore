package com.estore.customer.dao;

import org.springframework.data.repository.CrudRepository;

import com.estore.customer.model.Customer;

public interface AddressDao extends CrudRepository<Customer, Long> {

}
