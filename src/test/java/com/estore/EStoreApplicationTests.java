package com.estore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.estore.order.model.CreditCardPayment;
import com.estore.order.model.Order;
import com.estore.order.model.OrderDetail;
import com.estore.order.model.PayPalPayment;
import com.estore.order.model.PaymentMethod;
import com.estore.order.model.ShippingOrder;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EStoreApplicationTests {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void getInventoryById() throws Exception {
		mvc.perform(get("/inventory/1")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.notNullValue()))
				.andExpect(jsonPath("$.price", Matchers.is(150.0)));
	}
	
	@Test
	public void addInventory() throws Exception {
		Customer juliaCicale = customerService.getCustomerById(Long.valueOf(1));
		Inventory inventory = inventoryService.getInventoryById(Long.valueOf(1));
		
		/*Order juliaOrder = new Order(); 
		juliaOrder.setOrderId(1L);
				 juliaOrder.setBillingAddress(juliaCicale.getBillingAddress());
				 juliaOrder.setCustomer(juliaCicale);
				 juliaOrder.setOrderState(EStoreConstants.ORDER_STATUS_PENDING);
				 juliaOrder.setPaymentStatus(EStoreConstants.PAYMENT_STATUS_PENDING);
				  List<OrderDetail> orderDetails = new ArrayList<>();
				  orderDetails.add(new ShippingOrder(1L, inventory, 1,
				  EStoreConstants.ORDER_STATUS_PENDING, juliaCicale.getShippingAddress(), null)); 
				  juliaOrder.setOrderDetails(orderDetails);
				  System.out.println("Total of Order is : " + juliaOrder.getTotal());
				   List<PaymentMethod> paymentMethods = new ArrayList<>(); 
				   paymentMethods.add(new CreditCardPayment(1L,EStoreConstants.PAYMENT_STATUS_PENDING, 100.0, "1010101010101010101020",
				  "Julia Cicale", "911", "20/20")); 
				   paymentMethods.add(new PayPalPayment(2L, EStoreConstants.PAYMENT_STATUS_PENDING, 190.0, "XVF1022","julia.cicale@gmail.com"));
				   juliaOrder.setPaymentMethod(paymentMethods);*/
		mvc.perform(post("/inventory").content(EStoreUtils.asJsonString(inventory)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.price", Matchers.is(150.0)));
	}

}
