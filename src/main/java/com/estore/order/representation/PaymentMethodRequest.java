package com.estore.order.representation;

public class PaymentMethodRequest {
	private Double subTotal;
	private String creditCardNumber;
	private String nameOnCard;
	private String securityCode;
	private String validDate;
	String transactionId;
	String accountEmail;

	public PaymentMethodRequest() {
	}

	public PaymentMethodRequest(Double subTotal, String creditCardNumber, String nameOnCard, String securityCode,
			String validDate, String transactionId, String accountEmail) {
		this.subTotal = subTotal;
		this.creditCardNumber = creditCardNumber;
		this.nameOnCard = nameOnCard;
		this.securityCode = securityCode;
		this.validDate = validDate;
		this.transactionId = transactionId;
		this.accountEmail = accountEmail;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
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
