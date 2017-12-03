package com.estore.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.customer.dao.CustomerDao;
import com.estore.customer.model.Customer;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;

	public Iterable<Customer> listAllCustomers() {
		Iterable<Customer> listCustomer = customerDao.findAll();
		return listCustomer;
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

	public boolean updateCustomer(Customer customer) {
		customer = customerDao.save(customer);
		return customer != null;
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
