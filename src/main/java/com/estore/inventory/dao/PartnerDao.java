package com.estore.inventory.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.estore.inventory.model.Partner;

public interface PartnerDao extends CrudRepository<Partner, Long> {

	List<Partner> list_Partner_by_userName(@Param("userName") String userName);

}
