package com.estore.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.estore.inventory.model.Product;

public interface ProductDao extends CrudRepository<Product, Long> {

}
