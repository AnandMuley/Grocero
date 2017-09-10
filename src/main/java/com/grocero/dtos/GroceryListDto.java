package com.grocero.dtos;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Scope("prototype")
@Component
public class GroceryListDto {

    private String id;
    private String name;
    private List<ProductDto> items = new ArrayList<ProductDto>();

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

    public List<ProductDto> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "GroceryListDto [id=" + id + ", name=" + name + ", items="
                + items + "]";
    }

}
