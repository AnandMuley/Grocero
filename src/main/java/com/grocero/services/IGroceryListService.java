package com.grocero.services;

import com.grocero.dtos.GroceryListDto;

import java.util.List;

/**
 * Created by andriox on 16/9/17.
 */
public interface IGroceryListService {
    GroceryListDto findById(String listId);

    void create(GroceryListDto groceryListDto);

    List<GroceryListDto> fetchAll();

    void delete(String listId);

    void update(GroceryListDto groceryListDto);
}
