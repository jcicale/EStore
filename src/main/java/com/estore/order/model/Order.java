package com.estore.order.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.estore.customer.model.Address;
import com.estore.customer.model.Customer;

@Entity
@Table(name = "tblOrder")
@NamedQueries({
		@NamedQuery(query = "FROM Order o WHERE o.customer.customerId = :customerId", name = "Order.list_Orders_by_customerId"),
		@NamedQuery(query = "FROM Order o WHERE o.customer.customerId = :customerId AND o.orderState = :orderState", name = "Order.list_Orders_by_customerId_and_orderState") })

public class Order {
	@Id
	@GeneratedValue
	private Long orderId;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(nullable = false)
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
	private String paymentStatus;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(nullable = false)
	private Customer customer;
	private String orderState;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(nullable = false)
	private Address billingAddress;
	private Double total;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(nullable = false)
	private List<PaymentMethod> paymentMethod;

	public Order() {
	}

	public Order(Long orderId, List<OrderDetail> orderDetails, String paymentStatus, Customer customer,
			String orderState, Address billingAddress, Double total, List<PaymentMethod> paymentMethod) {
		this.orderId = orderId;
		this.orderDetails = orderDetails;
		this.paymentStatus = paymentStatus;
		this.customer = customer;
		this.orderState = orderState;
		this.billingAddress = billingAddress;
		this.total = total;
		this.paymentMethod = paymentMethod;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		if (orderDetails.size() > 0) {
			this.total = 0.0;
			for (OrderDetail orderDetail : orderDetails) {
				this.total += orderDetail.getSubTotal();
			}
		}
		this.orderDetails = orderDetails;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<PaymentMethod> getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(List<PaymentMethod> paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
