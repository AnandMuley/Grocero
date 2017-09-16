package com.grocero.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
@Document(collection = "customers")
public class CustomerBean {

    private String id;
    private String name;

    public CustomerBean() {
    }

    public CustomerBean(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CustomerBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
