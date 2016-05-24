package com.grocero.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.grocero.beans.GroceryListBean;

@Repository
public interface GroceryListRepository extends
		MongoRepository<GroceryListBean, String> {

}
