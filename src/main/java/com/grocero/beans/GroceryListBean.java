package com.grocero.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class GroceryListBean {

	@Id
	private String id;
	private List<ProductBean> items = new ArrayList<ProductBean>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ProductBean> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return "GroceryListBean [id=" + id + ", items=" + items + "]";
	}

}
