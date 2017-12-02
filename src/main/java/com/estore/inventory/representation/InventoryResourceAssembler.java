package com.estore.inventory.representation;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.estore.inventory.model.Inventory;
import com.estore.inventory.resource.InventoryResource;

public class InventoryResourceAssembler extends ResourceAssemblerSupport<Inventory, InventoryRepresentation> {

	public InventoryResourceAssembler() {
		super(InventoryResource.class, InventoryRepresentation.class);
	}

	@Override
	public InventoryRepresentation toResource(Inventory inventory) {
		return createResourceWithId(inventory.getInventoryId(), inventory);
	}

}
