package com.grocero.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "grocerylists")
public class GroceryListBean {

    @Id
    private String id;
    private String name;
    private List<ProductBean> items = new ArrayList<>();

    public GroceryListBean() {
    }

    public GroceryListBean(String name) {
        super();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductBean> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GroceryListBean [id=" + id + ", name=" + name + ", items="
                + items + "]";
    }

}
