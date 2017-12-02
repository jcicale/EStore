package com.estore.order.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.springframework.hateoas.ResourceSupport;

@Entity
@Table(name = "tblPaymentMethod")
@Inheritance(strategy = InheritanceType.JOINED)
public class PaymentMethod extends ResourceSupport {
	@Id
	@GeneratedValue
	private Long paymentId;
	private String paymentStatus;
	private Double subTotal;

	public PaymentMethod() {

	}

	public PaymentMethod(Long paymentId, String paymentStatus, double subTotal) {
		this.paymentId = paymentId;
		this.paymentStatus = paymentStatus;
		this.subTotal = subTotal;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

}
