package com.grocero.utils;

import org.springframework.stereotype.Component;

import com.grocero.beans.GroceryListBean;
import com.grocero.beans.ProductBean;
import com.grocero.dtos.GroceryListDto;
import com.grocero.dtos.ProductDto;

@Component
public class BeanCreatorUtil {

    public GroceryListBean createGroceryListBean(GroceryListDto groceryListDto) {
        GroceryListBean groceryListBean = new GroceryListBean(
                groceryListDto.getName());
        groceryListBean.getItems().addAll(groceryListDto.getItems());
        return groceryListBean;
    }

    public ProductBean createProductBean(ProductDto productDto) {
        ProductBean productBean = new ProductBean();
        productBean.setMeasuredIn(productDto.getMeasuredIn());
        productBean.setName(productDto.getName());
        return productBean;
    }

}
