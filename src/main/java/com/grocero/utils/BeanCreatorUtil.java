package com.grocero.utils;

import com.grocero.beans.GroceryListBean;
import com.grocero.beans.ProductBean;
import com.grocero.dtos.GroceryListDto;
import com.grocero.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class BeanCreatorUtil {

    @Autowired
    private ApplicationContext context;

    public GroceryListBean create(GroceryListDto groceryListDto, Function<ProductDto, ProductBean> function) {
        GroceryListBean groceryListBean = context.getBean(GroceryListBean.class, groceryListDto.getName());
        groceryListBean.getItems().addAll(groceryListDto.getItems().stream().map(function::apply).collect(Collectors.toList()));
        return groceryListBean;
    }

    public GroceryListBean update(GroceryListDto groceryListDto) {
        GroceryListBean groceryListBean = context.getBean(GroceryListBean.class, groceryListDto.getId(), groceryListDto.getName());
        groceryListBean.getItems().addAll(groceryListDto.getItems().stream().map(this::create).collect(Collectors.toList()));
        return groceryListBean;
    }

    public ProductBean create(ProductDto productDto) {
        ProductBean.Builder builder = context.getBean(ProductBean.Builder.class);
        return builder.setId(productDto.getId()).setName(productDto.getName())
                .setMeasuredIn(productDto.getMeasuredIn()).setQuantity(productDto.getQuantity())
                .setCost(productDto.getCost()).build();
    }


}
