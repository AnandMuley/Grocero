package com.grocero.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.grocero.beans.GroceryListBean;
import com.grocero.beans.ProductBean;
import com.grocero.dtos.GroceryListDto;
import com.grocero.dtos.ProductDto;

import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class DtoCreatorUtil {

    @Autowired
    private ApplicationContext context;

    public GroceryListDto createGroceryListDto(GroceryListBean groceryListBean) {
        GroceryListDto groceryListDto = context.getBean(GroceryListDto.class);
        groceryListDto.setName(groceryListBean.getName());
        groceryListDto.setId(groceryListBean.getId());
        groceryListDto.getItems().addAll(groceryListBean.getItems().stream().map(this::createProductDto).collect(Collectors.toList()));
        return groceryListDto;
    }

    public ProductDto createProductDto(ProductBean productBean) {
        ProductDto productDto = context.getBean(ProductDto.class);
        productDto.setId(productBean.getId());
        productDto.setMeasuredIn(productBean.getMeasuredIn());
        productDto.setName(productBean.getName());
        return productDto;
    }

}
