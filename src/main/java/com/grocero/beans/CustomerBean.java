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

    public CustomerBean(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {

        Builder withId(String idVal) {
            return this;
        }

        public void build() {

        }

    }

    @Override
    public String toString() {
        return "CustomerBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
