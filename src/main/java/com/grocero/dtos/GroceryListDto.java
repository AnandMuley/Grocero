package com.grocero.dtos;

import java.util.ArrayList;
import java.util.List;

public class GroceryListDto {

	private String id;
	private List<ProductDto> items = new ArrayList<ProductDto>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ProductDto> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return "GroceryListDto [id=" + id + ", items=" + items + "]";
	}

}
