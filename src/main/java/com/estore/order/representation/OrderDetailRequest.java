package com.estore.order.representation;

public class OrderDetailRequest {
	private Long inventoryId;
	private Integer quantity;
	private Long addressId;

	public OrderDetailRequest() {
	}

	public OrderDetailRequest(Long inventoryId, Integer quantity, Long addressId) {
		this.inventoryId = inventoryId;
		this.quantity = quantity;
		this.addressId = addressId;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
