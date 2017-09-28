package com.grocero.dtos;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Scope("prototype")
@Component
public class MasterListDto {

    private String id;
    private String customerId;
    private Set<String> items = new LinkedHashSet<>();
    private String name;

    public MasterListDto() {
    }

    public MasterListDto(String id, String customerId, String name) {
        this.id = id;
        this.customerId = customerId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Set<String> getItems() {
        return items;
    }

    public void setItems(Set<String> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MasterListDto{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
