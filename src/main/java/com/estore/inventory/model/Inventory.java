package com.estore.inventory.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.hateoas.ResourceSupport;

@Entity
@Table(name = "tblInventory")
@NamedQueries({
		@NamedQuery(query = "FROM Inventory i WHERE i.quantity > 0 AND i.product.title LIKE :keywords ORDER BY i.price", name = "Inventory.list_Inventory_by_keywords") })

public class Inventory extends ResourceSupport {
	@Id
	@GeneratedValue
	private Long inventoryId;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(nullable = false)
	private Partner partner;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(nullable = false)
	private Product product;
	private Double price;
	private Integer quantity;

	public Inventory() {
	}

	public Inventory(Long inventoryId, Partner partner, Product product, Double price, Integer quantity) {
		this.inventoryId = inventoryId;
		this.partner = partner;
		this.product = product;
		this.price = price;
		this.quantity = quantity;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
