package com.estore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.estore.customer.model.Customer;
import com.estore.customer.service.CustomerService;
import com.estore.inventory.model.Inventory;
import com.estore.inventory.service.InventoryService;
import com.estore.order.representation.OrderDetailRequest;
import com.estore.order.representation.OrderRequest;
import com.estore.order.representation.PaymentMethodRequest;
import com.estore.order.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderResourceTests {

	@Autowired
	OrderService orderService;

	@Autowired
	CustomerService customerService;

	@Autowired
	InventoryService inventoryService;

	@Autowired
	private MockMvc mvc;

	@Test
	public void addOrder() throws Exception {
		Customer customer = customerService.getCustomerById(Long.valueOf(1));
		Inventory inventory = inventoryService.getInventoryById(Long.valueOf(1));
		List<PaymentMethodRequest> paymentMethodRequest = new ArrayList<>();
		paymentMethodRequest.add(new PaymentMethodRequest(inventory.getPrice(), "1010101010101010101020",
				"Julia Cicale", "911", "20/20", null, null));
		List<OrderDetailRequest> orderDetailRequest = new ArrayList<>();
		orderDetailRequest.add(
				new OrderDetailRequest(inventory.getInventoryId(), 1, customer.getShippingAddress().getAddressId()));
		OrderRequest orderRequest = new OrderRequest(orderDetailRequest, customer.getCustomerId(),
				customer.getBillingAddress().getAddressId(), paymentMethodRequest);
		mvc.perform(post("/order").content(EStoreUtils.asJsonString(orderRequest))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.orderDetails[0].inventory.inventoryId", Matchers.is(1)));
	}

	@Test
	public void acceptPayment() throws Exception {
		mvc.perform(put("/order/1/accept")).andExpect(status().isOk());
	}

}
