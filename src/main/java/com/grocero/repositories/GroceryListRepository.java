package com.grocero.repositories;

import com.grocero.beans.GroceryListBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroceryListRepository extends MongoRepository<GroceryListBean, String> {

    List<GroceryListBean> findByCustomerId(String customerId);

}
