package com.grocero.services.impl;

import com.grocero.beans.AbstractAuthenticationBean;
import com.grocero.beans.CustomerBean;
import com.grocero.beans.MasterListBean;
import com.grocero.common.DateUtil;
import com.grocero.dtos.AuthenticationDto;
import com.grocero.dtos.CustomerDto;
import com.grocero.dtos.MasterListDto;
import com.grocero.exceptions.CustomerDoesNotExistException;
import com.grocero.exceptions.CustomerServiceException;
import com.grocero.exceptions.NoDataFoundException;
import com.grocero.repositories.CustomerRepository;
import com.grocero.repositories.MasterListRepository;
import com.grocero.services.AuthenticationService;
import com.grocero.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl extends GroceroService implements CustomerService, AuthenticationService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MasterListRepository masterListRepository;

    public void createOrUpdate(CustomerDto customerDto) {
        CustomerBean customerBean = customerRepository.save(dtoToBeanMapper.map(customerDto));
        customerDto.setId(customerBean.getId());
    }

    public Set<CustomerDto> getAll() throws NoDataFoundException {
        List<CustomerBean> customers = customerRepository.findAll();
        if (CollectionUtils.isEmpty(customers)) {
            throw new NoDataFoundException("No customers found");
        }
        return customers.stream().map(beanToDtoMapper::map).collect(Collectors.toSet());
    }

    public void createOrUpdate(MasterListDto masterListDto) throws CustomerServiceException {
        Optional.ofNullable(customerRepository.findOne(masterListDto.getCustomerId())).orElseThrow(CustomerDoesNotExistException::new);
        MasterListBean existingMasterList = masterListRepository.findOneByCustomerId(masterListDto.getCustomerId());
        MasterListBean masterListBean = masterListRepository.save(dtoToBeanMapper.map(masterListDto, Optional.ofNullable(existingMasterList)));
        masterListDto.setId(masterListBean.getId());
    }

    @Override
    public MasterListDto getMasterList(String customerId) throws NoDataFoundException {
        MasterListBean masterList = masterListRepository.findOneByCustomerId(customerId);
        if (masterList == null) {
            throw new NoDataFoundException("No master lists found");
        }
        return beanToDtoMapper.map(masterList);
    }

    @Override
    public Optional<CustomerDto> findByUsernameAndPassword(String username, String password) {
        CustomerBean customerBean = customerRepository.findOneByUsernameAndPassword(username, password);
        return Optional.ofNullable(customerBean).map(bean -> {
            bean.setAuthToken(UUID.randomUUID().toString());
            bean.setAuthTokenValidTill(DateUtil.getTokenValidity());
            return customerRepository.save(bean);
        }).map(beanToDtoMapper::map);
    }

    @Override
    public Optional<AuthenticationDto> authenticate(String username, String authToken) {
        return Optional.ofNullable(customerRepository.findOneByUsernameAndAuthToken(username, authToken))
                .filter(bean -> bean.getAuthTokenValidTill().isAfter(LocalDateTime.now()))
                .map((AbstractAuthenticationBean bean) -> beanToDtoMapper.map(bean));
    }
}
