package com.estore.order.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tblPayPalPayment")
public class PayPalPayment extends PaymentMethod {
	String transactionId;
	String accountEmail;

	public PayPalPayment() {

	}

	public PayPalPayment(Long paymentId, String paymentStatus, Double subTotal, String transactionId,
			String accountEmail) {
		super(paymentId, paymentStatus, subTotal);
		this.transactionId = transactionId;
		this.accountEmail = accountEmail;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

}
