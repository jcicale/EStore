package com.estore.inventory.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.SuperLink;
import com.estore.inventory.dao.ProductDao;
import com.estore.inventory.model.Product;
import com.estore.inventory.resource.ProductResource;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	public Iterable<Product> listAllProduct() {
		Iterable<Product> listProduct = productDao.findAll();
		for (Product product : listProduct) {
			product.add(new SuperLink(linkTo(methodOn(ProductResource.class).deleteProduct(product.getProductId())).withRel("delete"), "DELETE"));
		}
		return listProduct;
	}

	public Product getProductById(Long productId) {
		Product product = productDao.findOne(productId);
		return product;
	}

	public Product addProduct(Product product) {
		product.setProductId(null);
		product = productDao.save(product);
		return product;
	}

	public Product updateProduct(Product product) {
		product = productDao.save(product);
		return product;
	}

	public boolean deleteProduct(Long productId) {
		try {
			productDao.delete(productId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
