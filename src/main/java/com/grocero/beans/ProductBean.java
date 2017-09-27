package com.grocero.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Scope(value = "prototype")
@Component
@Document(collection = "products")
public class ProductBean implements Serializable {

    @Id
    private String id;
    private String name;
    private String measuredIn;
    private Integer quantity;
    private Double cost;

    public ProductBean() {
    }

    public ProductBean(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.measuredIn = builder.measuredIn;
        this.quantity = builder.quantity;
        this.cost = builder.cost;
    }

    @Component
    public static class Builder {
        private String id;
        private String name;
        private String measuredIn;
        private Integer quantity;
        private Double cost;

        public ProductBean build() {
            return new ProductBean(this);
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setMeasuredIn(String measuredIn) {
            this.measuredIn = measuredIn;
            return this;
        }

        public Builder setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setCost(Double cost) {
            this.cost = cost;
            return this;
        }
    }

    public ProductBean(String id, String name, String measuredIn) {
        super();
        this.id = id;
        this.name = name;
        this.measuredIn = measuredIn;
    }

    public ProductBean(String name, String measuredIn, Integer quantity) {
        this.name = name;
        this.measuredIn = measuredIn;
        this.quantity = quantity;
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

    public String getMeasuredIn() {
        return measuredIn;
    }

    public void setMeasuredIn(String measuredIn) {
        this.measuredIn = measuredIn;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductBean that = (ProductBean) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!measuredIn.equals(that.measuredIn)) return false;
        if (!quantity.equals(that.quantity)) return false;
        return cost != null ? cost.equals(that.cost) : that.cost == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + measuredIn.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductBean [id=" + id + ", name=" + name + ", measuredIn="
                + measuredIn + ", quantity=" + quantity + ", cost=" + cost
                + "]";
    }

}
