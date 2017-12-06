package com.estore.inventory.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estore.inventory.model.Partner;
import com.estore.inventory.service.PartnerService;

@RestController
@RequestMapping("partner")
public class PartnerResource {

	@Autowired
	private PartnerService partnerService;

	@GetMapping
	public Iterable<Partner> listPartner() {
		return partnerService.listAllPartner();
	}

	@GetMapping("{partnerId}")
	public Partner getPartnerById(@PathVariable("partnerId") Long partnerId) {
		return partnerService.getPartnerById(partnerId);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Partner> updatePartner(@RequestBody Partner partner) {
		partner = partnerService.updatePartner(partner);
		if (partner != null) {
			return new ResponseEntity<Partner>(partner, HttpStatus.OK);
		}
		return new ResponseEntity<Partner>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Partner addProduct(@RequestBody Partner partner) {
		partner = partnerService.addPartner(partner);
		return partner;
	}

	@RequestMapping(value = "{partnerId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePartner(@PathVariable Long partnerId) {
		if (partnerService.deletePartner(partnerId)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
}
