package com.springbootfundamentals.springmvcrest;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.springbootfundamentals.springmvcrest.entities.Product;
import com.springbootfundamentals.springmvcrest.repos.ProductRepository;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@RunWith(SpringRunner.class)
@WebMvcTest
class ProductRestControllerMvcTest {

	private static final String CONTEXT_URL = "/productapp";

	private static final String PRODUCT_URL = "/productapp/products";

	private static final int PRODUCT_PRICE = 1000;

	private static final String PRODUCT_DESCRIPTION_STRING = "Its Awesome";

	private static final String PRODUCT_NAME_STRING = "MacBook";

	private static final int PRODUCT_ID = 6;

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductRepository repository;
	
	@Test
	void testFindAll() throws Exception {
		Product product = buildProduct();
		List<Product> products = Arrays.asList(product);
		when(repository.findAll()).thenReturn(products);
			
		
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc.perform(get(PRODUCT_URL).contextPath(CONTEXT_URL))
			.andExpect(content().json(objectWriter.writeValueAsString(product)));
	}
	
	@Test
	public void testCreateProduct() throws JsonProcessingException, Exception {
		Product product = buildProduct();
		when(repository.save(any())).thenReturn(product);
		
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc.perform(post(PRODUCT_URL).contextPath(CONTEXT_URL)
				.contentType(MediaType.APPLICATION_JSON).content(objectWriter.writeValueAsString(product)))
				.andExpect(content().json(objectWriter.writeValueAsString(product)));
	}
	
	@Test
	public void testUpdateProduct() throws JsonProcessingException, Exception {
		Product product = buildProduct();
		product.setPrice(3000);
		when(repository.save(any())).thenReturn(product);
		
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc.perform(put(PRODUCT_URL).contextPath(CONTEXT_URL)
				.contentType(MediaType.APPLICATION_JSON).content(objectWriter.writeValueAsString(product)))
				.andExpect(content().json(objectWriter.writeValueAsString(product)));
	}

	@Test
	public void testDeleteProduct() throws Exception {
		doNothing().when(repository).deleteById(PRODUCT_ID);
		mockMvc.perform(delete(PRODUCT_URL+PRODUCT_ID).contextPath(CONTEXT_URL)).andExpect(status().isOk());
	}
	
	private Product buildProduct() {
		Product product = new Product();
		product.setId(PRODUCT_ID);
		product.setName(PRODUCT_NAME_STRING);
		product.setDescription(PRODUCT_DESCRIPTION_STRING);
		product.setPrice(PRODUCT_PRICE);
		return product;
	}

}
