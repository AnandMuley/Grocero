package com.grocero.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grocero.beans.ProductBean;
import com.grocero.dtos.ProductDto;
import com.grocero.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public void add(ProductDto productDto) {
		ProductBean productBean = new ProductBean();
		productBean.setMeasuredIn(productDto.getMeasuredIn());
		productBean.setName(productDto.getName());
		productRepository.save(productBean);
		productDto.setId(productBean.getId());
	}

	public List<ProductDto> getAll() {
		List<ProductBean> productBeans = productRepository.findAll();
		List<ProductDto> productDtos = new ArrayList<ProductDto>();
		for (ProductBean productBean : productBeans) {
			ProductDto productDto = new ProductDto(productBean.getId(),
					productBean.getName(), productBean.getMeasuredIn());
			productDtos.add(productDto);
		}
		return productDtos;
	}

	public ProductBean findByName(String name) {
		return productRepository.findByName(name);
	}

	public void update(ProductDto productDto) {
		ProductBean productBean = new ProductBean(productDto.getId(),
				productDto.getName(), productDto.getMeasuredIn());
		productRepository.save(productBean);
	}

}
