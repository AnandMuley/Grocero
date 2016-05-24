package com.grocero.dtos;

public class ProductDto {

	private String id;
	private String name;
	private String measuredIn;
	private Integer quantity;

	public ProductDto() {
	}

	public ProductDto(String id, String name, String measuredIn) {
		super();
		this.id = id;
		this.name = name;
		this.measuredIn = measuredIn;
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

	@Override
	public String toString() {
		return "ProductDto [id=" + id + ", name=" + name + ", measuredIn="
				+ measuredIn + ", quantity=" + quantity + "]";
	}

}
