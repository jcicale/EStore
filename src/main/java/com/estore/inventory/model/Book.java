package com.estore.inventory.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tblBook")
public class Book extends Product {
	private String isbn;
	private String author;

	public Book(Long productId, String title, String description, List<Review> review, String isbn, String author) {
		super(productId, title, description, review);
		this.isbn = isbn;
		this.author = author;
	}
	@JsonIgnore
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Book() {
	}

	public String getISBN() {
		return isbn;
	}

	public void setISBN(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
