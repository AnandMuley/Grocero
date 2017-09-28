package com.grocero.common;

import com.grocero.beans.CustomerBean;
import com.grocero.beans.GroceryListBean;
import com.grocero.beans.MasterListBean;
import com.grocero.beans.ProductBean;
import com.grocero.dtos.CustomerDto;
import com.grocero.dtos.GroceryListDto;
import com.grocero.dtos.MasterListDto;
import com.grocero.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BeanToDtoMapper {

    @Autowired
    private ApplicationContext context;

    public CustomerDto map(CustomerBean customerBean) {
        return context.getBean(CustomerDto.class, customerBean.getId(), customerBean.getName());
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
