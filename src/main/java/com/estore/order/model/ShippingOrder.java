package com.estore.order.model;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.estore.customer.model.Address;
import com.estore.inventory.model.Inventory;

@Entity
@Table(name = "tblShippingOrder")
public class ShippingOrder extends OrderDetail {
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Address shippingAddress;
	private Calendar estimatedDelivery;
	private String trackingNumber;
	private Calendar delivered;

	public ShippingOrder() {

	}

	public ShippingOrder(Long orderDetailId, Inventory inventory, Integer quantity, String orderState,
			Address shippingAddress, Calendar delivered) {
		super(orderDetailId, inventory, quantity, orderState);
		this.shippingAddress = shippingAddress;
		this.delivered = delivered;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public Calendar getDelivered() {
		return delivered;
	}

	public void setDelivered(Calendar delivered) {
		this.delivered = delivered;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Calendar getEstimatedDelivery() {
		return estimatedDelivery;
	}

	public void setEstimatedDelivery(Calendar estimatedDelivery) {
		this.estimatedDelivery = estimatedDelivery;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

}
