package com.grocero.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.grocero.common.BeanToDtoMapper;
import com.grocero.common.DtoToBeanMapper;
import com.grocero.common.OptionalList;
import com.grocero.exceptions.NoDataFoundException;
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
    public Optional<GroceryListDto> findById(String listId) {
        return Optional.ofNullable(groceryListRepository.findOne(listId)).map(beanToDtoMapper::map);
    }

    @Override
    public void create(GroceryListDto groceryListDto) {
        GroceryListBean groceryListBean = dtoToBeanMapper
                .map(groceryListDto, dtoToBeanMapper::map);
        groceryListRepository.save(groceryListBean);
        groceryListDto.setId(groceryListBean.getId());
    }

    @Override
    public List<GroceryListDto> fetchAll() throws NoDataFoundException {
        return OptionalList.ofNullable(groceryListRepository.findAll()).orElseThrow(NoDataFoundException::new).stream().map(beanToDtoMapper::map).collect(Collectors.toList());
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
