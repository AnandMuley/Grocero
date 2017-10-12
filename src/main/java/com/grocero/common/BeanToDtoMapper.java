package com.grocero.common;

import com.grocero.beans.*;
import com.grocero.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BeanToDtoMapper {

    @Autowired
    private ApplicationContext context;

    public AuthenticationDto map(AbstractAuthenticationBean authenticationBean) {
        AuthenticationDto authenticationDto = context.getBean("authenticationDto", AuthenticationDto.class);
        authenticationDto.setAuthToken(authenticationBean.getAuthToken());
        authenticationDto.setUsername(authenticationBean.getUsername());
        authenticationDto.setRole(authenticationBean.getRole());
        return authenticationDto;
    }

    public CustomerDto map(CustomerBean customerBean) {
        CustomerDto customerDto = context.getBean(CustomerDto.class, customerBean.getId(), customerBean.getName());
        customerDto.setAuthToken(customerBean.getAuthToken());
        customerDto.setUsername(customerBean.getUsername());
        return customerDto;
    }

    public MasterListDto map(MasterListBean masterListBean) {
        MasterListDto masterListDto = context.getBean(MasterListDto.class, masterListBean.getId(), masterListBean.getCustomerId(), masterListBean.getName());
        masterListDto.getItems().addAll(masterListBean.getItems());
        return masterListDto;
    }

    public GroceryListDto map(GroceryListBean groceryListBean) {
        GroceryListDto groceryListDto = context.getBean(GroceryListDto.class);
        groceryListDto.setName(groceryListBean.getName());
        groceryListDto.setId(groceryListBean.getId());
        groceryListDto.getItems().addAll(groceryListBean.getItems().stream().map(this::map).collect(Collectors.toList()));
        return groceryListDto;
    }

    public ProductDto map(ProductBean productBean) {
        ProductDto.Builder builder = context.getBean(ProductDto.Builder.class);
        return builder.setId(productBean.getId()).setName(productBean.getName())
                .setMeasuredIn(productBean.getMeasuredIn()).setQuantity(productBean.getQuantity())
                .setCost(productBean.getCost()).build();
    }

}
