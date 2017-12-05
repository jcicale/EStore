package com.estore.inventory.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.SuperLink;
import com.estore.inventory.dao.InventoryDao;
import com.estore.inventory.model.Inventory;
import com.estore.inventory.resource.InventoryResource;
import com.estore.order.resource.OrderResource;

@Service
public class InventoryService {

	@Autowired
	private InventoryDao inventoryDao;

	public Iterable<Inventory> listInventory() {
		Iterable<Inventory> listInventory = inventoryDao.findAll();
		for (Inventory inventory : listInventory) {
			inventory.add(linkTo(InventoryResource.class).slash(inventory.getInventoryId()).withSelfRel());
			inventory.add(new SuperLink(linkTo(OrderResource.class).withRel("save"),"POST"));
		}
		return listInventory;
	}

	public Iterable<Inventory> list_Inventory_by_keywords(String keywords) {
		Iterable<Inventory> listInventory = inventoryDao.list_Inventory_by_keywords("%" + keywords.trim().replace(" ", "%") + "%");
		for (Inventory inventory : listInventory) {
			inventory.add(linkTo(InventoryResource.class).slash(inventory.getInventoryId()).withSelfRel());
			inventory.add(new SuperLink(linkTo(OrderResource.class).withRel("save"),"POST"));
		}
		return listInventory;
	}
	
	public Iterable<Inventory> list_Inventory_by_partnerId(Long partnerId) {
		Iterable<Inventory> listInventory = inventoryDao.list_Inventory_by_partnerId(partnerId);
		for (Inventory inventory : listInventory) {
			//inventory.add(new SuperLink(linkTo(InventoryResource.class).withRel("save"),"POST"));
		}
		return listInventory;
	}

	public Inventory getInventoryById(Long inventoryId) {
		Inventory inventory = inventoryDao.findOne(inventoryId);
		inventory.add(linkTo(InventoryResource.class).slash(inventory.getInventoryId()).withSelfRel());
		inventory.add(new SuperLink(linkTo(OrderResource.class).withRel("save"),"POST"));
		return inventory;
	}

	public Inventory addInventory(Inventory inventory) {
		inventory.setInventoryId(null);
		inventory = inventoryDao.save(inventory);
		return inventory;
	}

	public boolean updateInventory(Inventory inventory) {
		inventory = inventoryDao.save(inventory);
		return inventory != null;
	}

	public boolean deleteInventory(Long inventoryId) {
		try {
			inventoryDao.delete(inventoryId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
