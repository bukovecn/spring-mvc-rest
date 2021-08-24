package com.springbootfundamentals.springmvcrest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootfundamentals.springmvcrest.entities.Product;
import com.springbootfundamentals.springmvcrest.repos.ProductRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class ProductRestController {

	@Autowired
	private ProductRepository repository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);
	
	@ApiOperation(value="Retrieves all the products", notes="A list of products", response=Product.class, responseContainer="List", produces="application/json")
	@GetMapping("/products")
	public List<Product> getProducts(){
		return repository.findAll();
	}
	
	@GetMapping("/products/{id}")
	public Product getProduct(@PathVariable int id) {
		LOGGER.info("Finding product by id: " + id);
		return repository.findById(id).get();
	}
	
	@PostMapping("/products")
	public Product createProduct(@RequestBody Product product) {
		return repository.save(product);
	}
	
	@PutMapping("/products")
	public Product updateProduct(@RequestBody Product product) {
		return repository.save(product);
	}
	
	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable int id) {
		repository.deleteById(id);
	}
}
