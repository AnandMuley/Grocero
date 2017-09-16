package com.grocero.common;

import com.grocero.beans.CustomerBean;
import com.grocero.dtos.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanToDtoMapper {

    @Autowired
    private ApplicationContext context;

    public CustomerDto map(CustomerBean customerBean) {
        return context.getBean(CustomerDto.class, customerBean.getId(), customerBean.getName());
    }

}
