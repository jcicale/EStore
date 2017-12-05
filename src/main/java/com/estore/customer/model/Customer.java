package com.estore.customer.model;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tblCustomer")
@NamedQueries({
		@NamedQuery(query = "FROM Customer c WHERE c.userName = :userName", name = "Customer.list_Customer_by_userName") })
public class Customer extends ResourceSupport {
	@Id
	@GeneratedValue
	private Long customerId;
	private String lastName;
	private String firstName;
	private String userName;
	private String password;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Address billingAddress;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Address shippingAddress;

	public Customer(Long customerId, String lastName, String firstName, String userName, String password,
			Address billingAddress, Address shippingAddress) {
		this.customerId = customerId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.userName = userName;
		this.password = password;
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
	}

	public Customer() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long id) {
		this.customerId = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
