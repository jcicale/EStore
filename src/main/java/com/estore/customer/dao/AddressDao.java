package com.estore.customer.dao;

import org.springframework.data.repository.CrudRepository;

import com.estore.customer.model.Address;

public interface AddressDao extends CrudRepository<Address, Long> {

}
