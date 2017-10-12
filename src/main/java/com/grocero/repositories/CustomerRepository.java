package com.grocero.repositories;

import com.grocero.beans.CustomerBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerBean, String> {

    CustomerBean findOneByUsernameAndPassword(String username, String password);

    CustomerBean findOneByUsernameAndAuthToken(String username, String authToken);

}
