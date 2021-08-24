package com.springbootfundamentals.springmvcrest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.springbootfundamentals.springmvcrest.entities.Product;

@SpringBootTest
class SpringmvcrestApplicationTests {

	@Value("${productapp.services.url}")
	private String baseURL;
	
	@Test
	void testGetProduct() {
		System.out.println(baseURL);
		RestTemplate temp = new RestTemplate();
		Product product = temp.getForObject(baseURL + "/4", Product.class);
		assertNotNull(product);
		assertEquals("Apple", product.getName());
	}
	
	@Test
	public void testCreateProduct() {
		RestTemplate temp = new RestTemplate();
		Product product = new Product();
		product.setName("Samsung mobile");
		product.setDescription("Awesome!");
		product.setPrice(1000);
		Product newProduct = temp.postForObject(baseURL, product, Product.class);
		assertNotNull(newProduct);
		assertNotNull(newProduct.getId());
		assertEquals("Samsung mobile", newProduct.getName());
	}
	
	@Test
	public void testUpdateProduct() {
		RestTemplate temp = new RestTemplate();
		Product product = temp.getForObject(baseURL + "/5", Product.class);
		product.setPrice(2000);
		temp.put(baseURL, product);
		
	}

}
