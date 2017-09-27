package com.grocero.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.grocero.beans.GroceryListBean;
import com.grocero.beans.ProductBean;
import com.grocero.dtos.GroceryListDto;
import com.grocero.dtos.ProductDto;

import java.util.stream.Collectors;

@Component
public class DtoCreatorUtil {

    @Autowired
    private ApplicationContext context;

    public GroceryListDto create(GroceryListBean groceryListBean) {
        GroceryListDto groceryListDto = context.getBean(GroceryListDto.class);
        groceryListDto.setName(groceryListBean.getName());
        groceryListDto.setId(groceryListBean.getId());
        groceryListDto.getItems().addAll(groceryListBean.getItems().stream().map(this::create).collect(Collectors.toList()));
        return groceryListDto;
    }

    public ProductDto create(ProductBean productBean) {
        ProductDto.Builder builder = context.getBean(ProductDto.Builder.class);
        return builder.setId(productBean.getId()).setName(productBean.getName())
                .setMeasuredIn(productBean.getMeasuredIn()).setQuantity(productBean.getQuantity())
                .setCost(productBean.getCost()).build();
    }

}
