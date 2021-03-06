package com.estore.inventory.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.inventory.model.Inventory;
import com.estore.inventory.service.InventoryService;

@RestController
@RequestMapping("inventory")
public class InventoryResource {

	@Autowired
	private InventoryService inventoryService;

	@GetMapping
	public Iterable<Inventory> listInventory() {
		return inventoryService.listInventory();
	}

	@RequestMapping(value = "product", method = RequestMethod.GET)
	public Iterable<Inventory> listInventoryByKeywords(@RequestParam("title") String title) {
		return inventoryService.list_Inventory_by_keywords(title);
	}

	@GetMapping("partnerId/{partnerId}")
	public Iterable<Inventory> listAllByPartnerId(@PathVariable("partnerId") Long partnerId) {
		System.out.println("testing");
		return inventoryService.list_Inventory_by_partnerId(partnerId);
	}

	@GetMapping("{inventoryId}")
	public Inventory getInventoryById(@PathVariable("inventoryId") Long inventoryId) {
		return inventoryService.getInventoryById(inventoryId);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<String> updateInventory(@RequestBody Inventory inventory) {
		if (inventoryService.updateInventory(inventory)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "{inventoryId}/increase", method = RequestMethod.PUT)
	public ResponseEntity<Inventory> increaseByOne(@PathVariable("inventoryId") Long inventoryId) {
		Inventory inventory = inventoryService.increaseByOne(inventoryService.getInventoryById(inventoryId));
		if (inventory != null) {
			return new ResponseEntity<Inventory>(inventory, HttpStatus.OK);
		}
		return new ResponseEntity<Inventory>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Inventory addInventory(@RequestBody Inventory inventory) {
		inventory = inventoryService.addInventory(inventory);
		return inventory;
	}
}
