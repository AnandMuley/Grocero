package com.grocero.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

public class CustomerBeanBuilder {

    private String id;
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerBeanInner build() {
        return new CustomerBeanInner();
    }

    @Scope("prototype")
    @Component
    @Document(collection = "customers")
    public class CustomerBeanInner {

        public CustomerBeanInner() {

        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
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
