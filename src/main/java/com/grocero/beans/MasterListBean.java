package com.grocero.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Scope("prototype")
@Component
@Document(collection = "masterlists")
public class MasterListBean {

    @Id
    private String id;
    private String name;
    private Set<String> items = new LinkedHashSet<>();
    private String customerId;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setItems(Set<String> items) {
        this.items = items;
    }

    public Set<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "MasterListBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", items=" + items +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
