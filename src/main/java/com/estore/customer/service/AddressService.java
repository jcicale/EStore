package com.estore.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.customer.dao.AddressDao;
import com.estore.customer.model.Address;

@Service
public class AddressService {

	@Autowired
	private AddressDao addressDao;

	public Iterable<Address> listAllAddresses() {
		Iterable<Address> listAddress = addressDao.findAll();
		return listAddress;
	}

	public Address getAddressById(Long addressId) {
		Address address = addressDao.findOne(addressId);
		return address;
	}

	public Address addAddress(Address address) {
		address.setAddressId(null);
		address = addressDao.save(address);
		return address;
	}

	public boolean updateAddress(Address address) {
		address = addressDao.save(address);
		return address != null;
	}

	public boolean deleteAddress(Long addressId) {
		try {
			addressDao.delete(addressId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
