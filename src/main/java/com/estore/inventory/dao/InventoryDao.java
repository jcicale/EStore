package com.estore.inventory.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.estore.inventory.model.Inventory;

public interface InventoryDao extends CrudRepository<Inventory, Long> {

	List<Inventory> list_Inventory_by_keywords(@Param("keywords") String keywords);

}
