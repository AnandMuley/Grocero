package com.grocero.common;

import com.grocero.beans.CustomerBean;
import com.grocero.beans.MasterListBean;
import com.grocero.dtos.CustomerDto;
import com.grocero.dtos.MasterListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

@Component
public class DtoToBeanMapper {

    @Autowired
    private ApplicationContext context;

    private MasterListBean getNewMasterListBean() {
        return context.getBean(MasterListBean.class);
    }

    public MasterListBean map(MasterListDto masterListDto, Optional<MasterListBean> optional) {
        MasterListBean masterListBean = optional.orElseGet(this::getNewMasterListBean);
        masterListBean.setCustomerId(masterListDto.getCustomerId());
        masterListBean.setName(masterListDto.getName());
        masterListBean.getItems().addAll(masterListDto.getItems());
        return masterListBean;
    }

    public CustomerBean map(CustomerDto customerDto) {
        return context.getBean(CustomerBean.class, customerDto.getName());
    }

}
