package com.grocero.repositories;

import com.grocero.beans.MasterListBean;
import com.grocero.dtos.MasterListDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterListRepository extends MongoRepository<MasterListBean, String> {

    List<MasterListBean> findByCustomerId(String customerId);

}
