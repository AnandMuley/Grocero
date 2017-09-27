package com.grocero.repositories;

import com.grocero.beans.MasterListBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterListRepository extends MongoRepository<MasterListBean, String> {

    MasterListBean findOneByCustomerId(String customerId);

}
