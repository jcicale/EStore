package com.estore.inventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tblPartner")
@NamedQueries({
	@NamedQuery(query = "FROM Partner p WHERE p.userName = :userName", name = "Partner.list_Partner_by_userName") })
public class Partner extends ResourceSupport {
	@Id
	@GeneratedValue
	private Long partnerId;
	private String name;
	private String userName;
	private String password;

	public Partner() {
	}

	public Partner(Long partnerId, String name, String userName, String password) {
		this.partnerId = partnerId;
		this.name = name;
		this.userName = userName;
		this.password = password;
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

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
