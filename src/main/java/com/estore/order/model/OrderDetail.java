package com.estore.order.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.hateoas.ResourceSupport;

import com.estore.inventory.model.Inventory;

@Entity
@Table(name = "tblOrderDetail")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
		@NamedQuery(query = "FROM OrderDetail o WHERE o.inventory.partner.partnerId = :partnerId", name = "OrderDetail.list_OrderDetails_by_partnerId"),
		@NamedQuery(query = "FROM OrderDetail o WHERE o.inventory.partner.partnerId = :partnerId AND o.orderState = :orderState", name = "OrderDetail.list_OrderDetails_by_partnerId_and_orderState") })
public class OrderDetail extends ResourceSupport {
	@Id
	@GeneratedValue
	private Long orderDetailId;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Inventory inventory;
	private Integer quantity;
	private Double subTotal;
	private String orderState;

	public OrderDetail() {

	}

	public OrderDetail(Long orderDetailId, Inventory inventory, Integer quantity, String orderState) {
		this.orderDetailId = orderDetailId;
		this.inventory = inventory;
		this.quantity = quantity;
		this.subTotal = inventory.getPrice() * this.quantity;
		this.orderState = orderState;
	}

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

}
