package com.estore.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.inventory.dao.PartnerDao;
import com.estore.inventory.model.Partner;

@Service
public class PartnerService {

	@Autowired
	private PartnerDao partnerDao;

	public Iterable<Partner> listAllPartner() {
		Iterable<Partner> listPartner = partnerDao.findAll();
		return listPartner;
	}

	public Partner getPartnerById(Long partnerId) {
		Partner partner = partnerDao.findOne(partnerId);
		return partner;
	}

	public Partner addPartner(Partner partner) {
		partner.setPartnerId(null);
		partner = partnerDao.save(partner);
		return partner;
	}

	public Partner updatePartner(Partner partner) {
		partner = partnerDao.save(partner);
		return partner;
	}

	public boolean deletePartner(Long partnerId) {
		try {
			partnerDao.delete(partnerId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
