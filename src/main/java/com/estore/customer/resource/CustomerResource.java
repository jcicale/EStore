package com.estore.customer.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estore.customer.model.Customer;
import com.estore.customer.representation.LoginRepresentation;
import com.estore.customer.service.CustomerService;

@RestController
@RequestMapping("customer")
public class CustomerResource {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public Iterable<Customer> listCustomer() {
		return customerService.listAllCustomers();
	}

	@GetMapping("{customerId}")
	public Customer getCustomerById(@PathVariable("customerId") Long customerId) {
		return customerService.getCustomerById(customerId);
	}

	@RequestMapping(value = "userLogin", method = RequestMethod.POST)
	public ResponseEntity<LoginRepresentation> userLogin(@RequestBody LoginRepresentation loginRepresentation) {
		loginRepresentation = customerService.userLogin(loginRepresentation);
		if (loginRepresentation != null) {
			return new ResponseEntity<LoginRepresentation>(loginRepresentation, HttpStatus.OK);
		}
		return new ResponseEntity<LoginRepresentation>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
		customer = customerService.updateCustomer(customer);
		if (customer != null) {
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		}
		return new ResponseEntity<Customer>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Customer addCustomer(@RequestBody Customer customer) {
		customer = customerService.addCustomer(customer);
		return customer;
	}

	@RequestMapping(value = "{customerId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
		if (customerService.deleteCustomer(customerId)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
}
