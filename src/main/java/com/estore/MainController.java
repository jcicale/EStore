package com.estore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estore.customer.model.Address;
import com.estore.customer.model.Customer;
import com.estore.customer.service.CustomerService;
import com.estore.inventory.model.Book;
import com.estore.inventory.model.Inventory;
import com.estore.inventory.model.Partner;
import com.estore.inventory.model.Product;
import com.estore.inventory.service.InventoryService;
import com.estore.inventory.service.PartnerService;
import com.estore.inventory.service.ProductService;

@Controller
public class MainController {

	@Autowired
	CustomerService customerService;

	@Autowired
	ProductService productService;

	@Autowired
	InventoryService inventoryService;

	@Autowired
	PartnerService partnerService;

	@RequestMapping("/")
	public String index() {
		// Testing addCustomer
		System.out.println("Adding Customer julia.cicale");
		Customer juliaCicale = customerService.getCustomerById(Long.valueOf(1));
		if (juliaCicale == null) {
			juliaCicale = customerService.addCustomer(
					new Customer(null, "Cicale", "Julia", "julia.cicale", "julia.cicale",
							new Address(null, "123 Home St.", "Unit 2", "Chicago", "IL", "60657"),
							new Address(null, "123 Business Rd.", "Ste 500", "Chicago", "IL", "60601")));
			// Adding products
			System.out.println("Adding Product FitBit Alta");
			Product fitbitAlta = productService.addProduct(new Product(null, "FitBit Alta", "Activity Tracker", null));
			System.out.println("Adding Product Bluetooth Headphones");
			Product headphones = productService.addProduct(
					new Product(null, "Bluetooth Headphones", "Wireless and comfortable headphones for running", null));
			System.out.println("Adding Product Laptop Bag");
			Product laptopBag = productService
					.addProduct(new Product(null, "Laptop Bag", "Fits laptops of up to 15 inches", null));
			Book odyssey = (Book) productService.addProduct(new Book(null, "The Odyssey",
					"Sing to me of the man, Muse, the man of twists and turns driven time and again off course, once he had plundered the hallowed heights of Troy",
					null, "9785040381791", "Homer"));
			// Adding Partners
			System.out.println("Adding Partner Amazon");
			Partner amazon = partnerService
					.addPartner(new Partner(null, "Amazon", "amazon", "amazon12345"));
			Inventory amazonInv_1 = inventoryService.addInventory(new Inventory(null, amazon, fitbitAlta, 150.0, 10));
			Inventory amazonInv_2 = inventoryService.addInventory(new Inventory(null, amazon, headphones, 80.0, 10));
			Inventory amazonInv_3 = inventoryService.addInventory(new Inventory(null, amazon, laptopBag, 15.0, 10));
			Inventory amazonInv_4 = inventoryService.addInventory(new Inventory(null, amazon, odyssey, 40.0, 10));

			System.out.println("Adding Partner Ebay");
			Partner ebay = partnerService
					.addPartner(new Partner(null, "Ebay", "ebay", "ebay12345"));
			Inventory ebayInv_1 = inventoryService.addInventory(new Inventory(null, ebay, fitbitAlta, 160.0, 15));
			Inventory ebayInv_2 = inventoryService.addInventory(new Inventory(null, ebay, headphones, 70.0, 15));
			Inventory ebayInv_3 = inventoryService.addInventory(new Inventory(null, ebay, laptopBag, 20.0, 15));
			Inventory ebayInv_4 = inventoryService.addInventory(new Inventory(null, ebay, odyssey, 40.0, 15));
		}

		return "index.html";
	}

}