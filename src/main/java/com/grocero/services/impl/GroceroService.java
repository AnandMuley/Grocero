package com.grocero.services.impl;

import com.grocero.common.BeanToDtoMapper;
import com.grocero.common.DtoToBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroceroService {

    @Autowired
    protected DtoToBeanMapper dtoToBeanMapper;

    @Autowired
    protected BeanToDtoMapper beanToDtoMapper;

}
