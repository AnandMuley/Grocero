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

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public GroceryListBean map(GroceryListDto groceryListDto, Function<ProductDto, ProductBean> function) {
        GroceryListBean groceryListBean = context.getBean(GroceryListBean.class, groceryListDto.getName());
        groceryListBean.getItems().addAll(groceryListDto.getItems().stream().map(function::apply).collect(Collectors.toList()));
        return groceryListBean;
    }

    public GroceryListBean map(GroceryListDto groceryListDto) {
        GroceryListBean groceryListBean = context.getBean(GroceryListBean.class, groceryListDto.getId(), groceryListDto.getName());
        groceryListBean.getItems().addAll(groceryListDto.getItems().stream().map(this::map).collect(Collectors.toList()));
        return groceryListBean;
    }

    public ProductBean map(ProductDto productDto) {
        ProductBean.Builder builder = context.getBean(ProductBean.Builder.class);
        return builder.setId(productDto.getId()).setName(productDto.getName())
                .setMeasuredIn(productDto.getMeasuredIn()).setQuantity(productDto.getQuantity())
                .setCost(productDto.getCost()).build();
    }

}
