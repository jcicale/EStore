package com.estore.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.inventory.dao.ProductDao;
import com.estore.inventory.model.Product;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	public Iterable<Product> listAllProduct() {
		Iterable<Product> listProduct = productDao.findAll();
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

	public boolean updateProduct(Product product) {
		product = productDao.save(product);
		return product != null;
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
