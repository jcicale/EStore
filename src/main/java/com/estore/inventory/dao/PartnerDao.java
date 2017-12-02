package com.estore.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.estore.inventory.model.Partner;

public interface PartnerDao extends CrudRepository<Partner, Long> {

}
