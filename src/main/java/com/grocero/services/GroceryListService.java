package com.grocero.services;

import com.grocero.dtos.GroceryListDto;

import java.util.List;

public interface GroceryListService {

    GroceryListDto findById(String listId);

    void create(GroceryListDto groceryListDto);

    List<GroceryListDto> fetchAll();

    void delete(String listId);

    void update(GroceryListDto groceryListDto);

}
