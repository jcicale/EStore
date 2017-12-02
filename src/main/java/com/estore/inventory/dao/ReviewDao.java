package com.estore.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.estore.inventory.model.Review;

public interface ReviewDao extends CrudRepository<Review, Long> {

}
