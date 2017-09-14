package com.grocero.dtos;

public class MasterListDto {

    private String id;
    private String customerId;
    private String name;

    public MasterListDto(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
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
}
