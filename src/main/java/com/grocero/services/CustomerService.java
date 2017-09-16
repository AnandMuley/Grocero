package com.grocero.services;

import com.grocero.beans.CustomerBean;
import com.grocero.dtos.CustomerDto;
import com.grocero.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends GroceroService {

    @Autowired
    private CustomerRepository customerRepository;

    public void save(CustomerDto customerDto) {
        CustomerBean customerBean = customerRepository.save(dtoToBeanMapper.map(customerDto));
        customerDto.setId(customerBean.getId());
    }
}
