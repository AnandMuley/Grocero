package com.grocero.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.grocero.common.BeanToDtoMapper;
import com.grocero.common.DtoToBeanMapper;
import com.grocero.services.GroceryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grocero.beans.GroceryListBean;
import com.grocero.dtos.GroceryListDto;
import com.grocero.repositories.GroceryListRepository;

@Service
public class GroceryListServiceImpl implements GroceryListService {

    @Autowired
    private GroceryListRepository groceryListRepository;

    @Autowired
    private BeanToDtoMapper beanToDtoMapper;

    @Autowired
    private DtoToBeanMapper dtoToBeanMapper;

    @Override
    public GroceryListDto findById(String listId) {
        GroceryListBean groceryListBean = groceryListRepository.findOne(listId);
        GroceryListDto groceryListDto = null;
        Optional.ofNullable(groceryListBean).ifPresent(bean ->
                beanToDtoMapper.map(bean)
        );
        return groceryListDto;
    }

    @Override
    public void create(GroceryListDto groceryListDto) {
        GroceryListBean groceryListBean = dtoToBeanMapper
                .map(groceryListDto, dtoToBeanMapper::map);
        groceryListRepository.save(groceryListBean);
        groceryListDto.setId(groceryListBean.getId());
    }

    @Override
    public List<GroceryListDto> fetchAll() {
        List<GroceryListBean> groceryListBeans = groceryListRepository
                .findAll();
        List<GroceryListDto> groceryListDtos = new ArrayList<GroceryListDto>();
        for (GroceryListBean groceryListBean : groceryListBeans) {
            GroceryListDto groceryListDto = beanToDtoMapper
                    .map(groceryListBean);
            groceryListDtos.add(groceryListDto);
        }
        return groceryListDtos;
    }

    @Override
    public void delete(String listId) {
        groceryListRepository.delete(listId);
    }

    @Override
    public void update(GroceryListDto groceryListDto) {
        GroceryListBean groceryListBean = dtoToBeanMapper
                .map(groceryListDto);
        groceryListBean.setId(groceryListDto.getId());
        groceryListRepository.save(groceryListBean);
    }

}
