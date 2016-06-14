package com.grocero.utils;

import org.springframework.stereotype.Component;

import com.grocero.beans.GroceryListBean;
import com.grocero.beans.ProductBean;
import com.grocero.dtos.GroceryListDto;
import com.grocero.dtos.ProductDto;

@Component
public class DtoCreatorUtil {

	public GroceryListDto createGroceryListDto(GroceryListBean groceryListBean) {
		GroceryListDto groceryListDto = new GroceryListDto();
		groceryListDto.setName(groceryListBean.getName());
		groceryListDto.setId(groceryListBean.getId());
		for (ProductBean productBean : groceryListBean.getItems()) {
			ProductDto productDto = createProductDto(productBean);
			productDto.setQuantity(productBean.getQuantity());
			productDto.setCost(productBean.getCost());
			productDto.setChecked(productBean.isChecked());
			groceryListDto.getItems().add(productDto);
		}
		return groceryListDto;
	}

	public ProductDto createProductDto(ProductBean productBean) {
		ProductDto productDto = new ProductDto();
		productDto.setId(productBean.getId());
		productDto.setMeasuredIn(productBean.getMeasuredIn());
		productDto.setName(productBean.getName());
		return productDto;
	}

}
