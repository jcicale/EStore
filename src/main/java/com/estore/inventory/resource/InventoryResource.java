package com.estore.inventory.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estore.inventory.model.Inventory;
import com.estore.inventory.service.InventoryService;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("inventory")
public class InventoryResource {

	@Autowired
	private InventoryService inventoryService;

	@GetMapping
	public Iterable<Inventory> listInventory() {
		return inventoryService.listInventory();
	}

	@GetMapping("product/{title}")
	public Iterable<Inventory> listInventoryByKeywords(@PathVariable("title") String title) {
		return inventoryService.list_Inventory_by_keywords(title);
	}

	@GetMapping("{inventoryId}")
	public Inventory getInventoryById(@PathVariable("inventoryId") Long inventoryId) {
		return inventoryService.getInventoryById(inventoryId);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity updateInventory(@RequestBody Inventory inventory) {
		if (inventoryService.updateInventory(inventory)) {
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Inventory addInventory(@RequestBody Inventory inventory) {
		inventory = inventoryService.addInventory(inventory);
		return inventory;
	}
}
