package com.grocero.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grocero.beans.ProductBean;
import com.grocero.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public void add(ProductBean productBean) {
		productRepository.save(productBean);
	}

	public List<ProductBean> getAll() {
		return productRepository.findAll();
	}

	public ProductBean findByName(String name) {
		return productRepository.findByName(name);
	}

}
