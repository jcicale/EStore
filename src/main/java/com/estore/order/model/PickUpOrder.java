package com.estore.order.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.estore.inventory.model.Inventory;

@Entity
@Table(name = "tblPickUpOrder")
public class PickUpOrder extends OrderDetail {

	private String timeForPickUp;

	public PickUpOrder() {

	}

	public PickUpOrder(Long orderDetailId, Inventory inventory, Integer quantity, String orderState) {
		super(orderDetailId, inventory, quantity, orderState);
	}

	public String getTimeForPickUp() {
		return timeForPickUp;
	}

	public void setTimeForPickUp(String timeForPickUp) {
		this.timeForPickUp = timeForPickUp;
	}

}
