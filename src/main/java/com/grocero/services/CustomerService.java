package com.grocero.services;

import com.grocero.dtos.CustomerDto;
import com.grocero.dtos.MasterListDto;
import com.grocero.exceptions.CustomerDoesNotExistException;
import com.grocero.exceptions.CustomerServiceException;
import com.grocero.exceptions.NoDataFoundException;

import java.util.Set;

public interface CustomerService {

    void save(CustomerDto customerDto);

    Set<CustomerDto> getAll() throws NoDataFoundException;

    void save(MasterListDto masterList) throws CustomerServiceException;

    MasterListDto getMasterList(String customerId) throws NoDataFoundException;

}