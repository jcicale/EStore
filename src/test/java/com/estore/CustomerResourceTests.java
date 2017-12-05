package com.estore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.estore.customer.representation.LoginRepresentation;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerResourceTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void listCustomers() throws Exception {
		LoginRepresentation loginRepresentation = new LoginRepresentation(null, "julia.cicale", null, "", null);
		String jsonObject = EStoreUtils.asJsonString(loginRepresentation);
		mvc.perform(post("/customer/userLogin").content(jsonObject).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.rol", Matchers.is("customer")));
	}

}
