package com.estore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.estore.inventory.model.Inventory;
import com.estore.inventory.service.InventoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InventoryResourceTests {

	@Autowired
	InventoryService inventoryService;

	@Autowired
	private MockMvc mvc;

	@Test
	public void listInventory() throws Exception {
		mvc.perform(get("/inventory/product/FitBit")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.notNullValue()))
		.andExpect(jsonPath("$", Matchers.hasSize(10)));
	}

	@Test
	public void getInventoryById() throws Exception {
		mvc.perform(get("/inventory/1")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.notNullValue()))
				.andExpect(jsonPath("$.price", Matchers.is(150.0)));
	}

	@Test@Ignore
	public void addInventory() throws Exception {
		Inventory inventory = inventoryService.getInventoryById(Long.valueOf(1));
		mvc.perform(post("/inventory").content(EStoreUtils.asJsonString(inventory))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.price", Matchers.is(150.0)));
	}

}
