package com.estore.order.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tblCreditCardPayment")
public class CreditCardPayment extends PaymentMethod {
	private String creditCardNumber;
	private String nameOnCard;
	private String securityCode;
	private String validDate;

	public CreditCardPayment() {

	}

	public CreditCardPayment(Long paymentId, String paymentStatus, Double subTotal, String creditCardNumber,
			String nameOnCard, String securityCode, String validDate) {
		super(paymentId, paymentStatus, subTotal);
		this.creditCardNumber = creditCardNumber;
		this.nameOnCard = nameOnCard;
		this.securityCode = securityCode;
		this.validDate = validDate;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

}
