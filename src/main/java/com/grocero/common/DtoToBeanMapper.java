package com.grocero.common;

import com.grocero.beans.CustomerBean;
import com.grocero.beans.MasterListBean;
import com.grocero.dtos.CustomerDto;
import com.grocero.dtos.MasterListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DtoToBeanMapper {

    @Autowired
    private ApplicationContext context;

    public MasterListBean map(MasterListDto masterListDto) {
        MasterListBean masterListBean = context.getBean(MasterListBean.class);
        masterListBean.setCustomerId(masterListDto.getCustomerId());
        masterListBean.setName(masterListDto.getName());
        return masterListBean;
    }

    public CustomerBean map(CustomerDto customerDto) {
        return context.getBean(CustomerBean.class, customerDto.getName());
    }

}
