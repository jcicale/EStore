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

import com.estore.inventory.model.Product;
import com.estore.inventory.service.ProductService;

@RestController
@RequestMapping("product")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@GetMapping
	public Iterable<Product> listProduct() {
		return productService.listAllProduct();
	}

	@GetMapping("{productId}")
	public Product getProductById(@PathVariable("productId") Long productId) {
		return productService.getProductById(productId);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		product = productService.updateProduct(product);
		if (product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		}
		return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Product addProduct(@RequestBody Product product) {
		product = productService.addProduct(product);
		return product;
	}

	@RequestMapping(value = "{orderId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteProduct(@RequestBody Long productId) {
		if (productService.deleteProduct(productId)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
}
