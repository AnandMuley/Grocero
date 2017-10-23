package com.grocero.dtos;

import com.grocero.beans.GroceryListBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Scope("prototype")
@Component
public class GroceryListDto {

    private String id;
    private String name;
    private String customerId;
    private List<ProductDto> items = new ArrayList<>();

    public GroceryListDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<ProductDto> items) {
        this.items = items;
    }

    public List<ProductDto> getItems() {
        return items;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "GroceryListDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", customerId='" + customerId + '\'' +
                ", items=" + items +
                '}';
    }

}
