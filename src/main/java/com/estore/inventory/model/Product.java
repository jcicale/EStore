package com.estore.inventory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.hateoas.ResourceSupport;

@Entity
@Table(name = "tblProduct")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product extends ResourceSupport {
	@Id
	@GeneratedValue
	private Long productId;
	private String title;
	private String description;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn
	private List<Review> review;

	public String getDescription() {
		return description;
	}

	public Product() {
	}

	public Product(Long productId, String title, String description, List<Review> review) {
		this.productId = productId;
		this.title = title;
		this.description = description;
		this.review = review;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
