package com.grocero.services;

import com.grocero.dtos.CustomerDto;
import com.grocero.dtos.MasterListDto;
import com.grocero.exceptions.CustomerServiceException;
import com.grocero.exceptions.NoDataFoundException;

import java.util.Optional;
import java.util.Set;

public interface CustomerService {

    void createOrUpdate(CustomerDto customerDto);

    Set<CustomerDto> getAll() throws NoDataFoundException;

    void createOrUpdate(MasterListDto masterList) throws CustomerServiceException;

    MasterListDto getMasterList(String customerId) throws NoDataFoundException;

    Optional<CustomerDto> findByUsernameAndPassword(String username, String password);

}