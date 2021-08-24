package com.springbootfundamentals.springmvcrest.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootfundamentals.springmvcrest.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
