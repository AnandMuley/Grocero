package com.grocero.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.grocero.beans.ProductBean;

public interface ProductRepository extends MongoRepository<ProductBean, String> {

	ProductBean findByName(String name);

}
