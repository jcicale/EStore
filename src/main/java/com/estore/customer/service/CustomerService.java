package com.estore.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.customer.dao.CustomerDao;
import com.estore.customer.model.Customer;
import com.estore.customer.representation.LoginRepresentation;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;

	public Iterable<Customer> listAllCustomers() {
		Iterable<Customer> listCustomer = customerDao.findAll();
		return listCustomer;
	}

	public LoginRepresentation userLogin(LoginRepresentation loginRepresentation) {
		List<Customer> customer = customerDao.list_Customer_by_userName(loginRepresentation.getUserName());
		if (customer != null && customer.size() > 0) {
			loginRepresentation.setRol("customer");
			loginRepresentation.setUserId(customer.get(0).getCustomerId());
			loginRepresentation.setName(customer.get(0).getFirstName() + " " + customer.get(0).getLastName());
		}
		return loginRepresentation;
	}

	public Customer getCustomerById(Long customerId) {
		Customer customer = customerDao.findOne(customerId);
		return customer;
	}

	public Customer addCustomer(Customer customer) {
		customer.setCustomerId(null);
		customer = customerDao.save(customer);
		return customer;
	}

	public Customer updateCustomer(Customer customer) {
		customer = customerDao.save(customer);
		return customer;
	}

	public boolean deleteCustomer(Long customerId) {
		try {
			customerDao.delete(customerId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
