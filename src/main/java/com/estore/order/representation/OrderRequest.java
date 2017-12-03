package com.estore.order.representation;

import java.util.List;

public class OrderRequest {
	private List<OrderDetailRequest> orderDetails;
	private Long customerId;
	private Long billingAddressId;
	private List<PaymentMethodRequest> paymentMethod;

	public OrderRequest() {
	}

	public OrderRequest(List<OrderDetailRequest> orderDetails, Long customerId, Long billingAddressId,
			List<PaymentMethodRequest> paymentMethod) {
		this.orderDetails = orderDetails;
		this.customerId = customerId;
		this.billingAddressId = billingAddressId;
		this.paymentMethod = paymentMethod;
	}

	public List<OrderDetailRequest> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailRequest> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<PaymentMethodRequest> getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(List<PaymentMethodRequest> paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Long getBillingAddressId() {
		return billingAddressId;
	}

	public void setBillingAddressId(Long billingAddressId) {
		this.billingAddressId = billingAddressId;
	}

}
