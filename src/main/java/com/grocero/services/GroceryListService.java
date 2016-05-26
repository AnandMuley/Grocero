package com.grocero.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grocero.beans.GroceryListBean;
import com.grocero.dtos.GroceryListDto;
import com.grocero.repositories.GroceryListRepository;
import com.grocero.utils.BeanCreatorUtil;
import com.grocero.utils.DtoCreatorUtil;

@Service
public class GroceryListService {

	@Autowired
	private GroceryListRepository groceryListRepository;

	@Autowired
	private DtoCreatorUtil dtoCreatorUtil;

	@Autowired
	private BeanCreatorUtil beanCreatorUtil;

	public GroceryListDto findById(String listId) {
		GroceryListBean groceryListBean = groceryListRepository.findOne(listId);
		GroceryListDto groceryListDto = dtoCreatorUtil
				.createGroceryListDto(groceryListBean);
		return groceryListDto;
	}

	public void create(GroceryListDto groceryListDto) {
		GroceryListBean groceryListBean = beanCreatorUtil
				.createGroceryListBean(groceryListDto);
		groceryListRepository.save(groceryListBean);
		groceryListDto.setId(groceryListBean.getId());
	}

	public List<GroceryListDto> fetchAll() {
		List<GroceryListBean> groceryListBeans = groceryListRepository
				.findAll();
		List<GroceryListDto> groceryListDtos = new ArrayList<GroceryListDto>();
		for (GroceryListBean groceryListBean : groceryListBeans) {
			GroceryListDto groceryListDto = dtoCreatorUtil
					.createGroceryListDto(groceryListBean);
			groceryListDtos.add(groceryListDto);
		}
		return groceryListDtos;
	}

	public void delete(String listId) {
		groceryListRepository.delete(listId);
	}

}
