package com.grocero.dtos;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Scope("prototype")
@Component
public class MasterListDto {

    private String id;
    private String customerId;
    private List<String> items = new ArrayList<>();
    private String name;

    public MasterListDto() {
    }

    public MasterListDto(String id, String customerId, String name) {
        this.id = id;
        this.customerId = customerId;
        this.name = name;
    }

    public List<String> getItems() {
        return items;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
