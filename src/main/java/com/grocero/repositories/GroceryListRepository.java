package com.grocero.repositories;

import com.grocero.beans.GroceryListBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroceryListRepository extends MongoRepository<GroceryListBean, String> {

}
