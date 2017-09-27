package com.grocero.dtos;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Scope("prototype")
@Component
public class ProductDto implements Serializable {

    private String id;
    private String name;
    private String measuredIn;
    private Integer quantity;
    private Double cost;

    public ProductDto() {
    }

    public ProductDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.measuredIn = builder.measuredIn;
        this.quantity = builder.quantity;
        this.cost = builder.cost;
    }

    public ProductDto(String id, String name, String measuredIn) {
        super();
        this.id = id;
        this.name = name;
        this.measuredIn = measuredIn;
    }

    @Component
    public static class Builder {
        private String id;
        private String name;
        private String measuredIn;
        private Integer quantity;
        private Double cost;

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

        public ProductDto build() {
            return new ProductDto(this);
        }
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

        ProductDto that = (ProductDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!name.equals(that.name)) return false;
        if (!measuredIn.equals(that.measuredIn)) return false;
        if (!quantity.equals(that.quantity)) return false;
        return cost != null ? cost.equals(that.cost) : that.cost == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + measuredIn.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductDto [id=" + id + ", name=" + name + ", measuredIn="
                + measuredIn + ", quantity=" + quantity + ", cost=" + cost
                + "]";
    }

}
