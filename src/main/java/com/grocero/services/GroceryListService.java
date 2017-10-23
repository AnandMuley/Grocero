package com.grocero.services;

import com.grocero.dtos.GroceryListDto;
import com.grocero.exceptions.NoDataFoundException;

import java.util.List;
import java.util.Optional;

public interface GroceryListService {

    Optional<GroceryListDto> findById(String listId);

    void create(GroceryListDto groceryListDto);

    List<GroceryListDto> fetchAll(String customerId) throws NoDataFoundException;

    void delete(String listId);

    void update(GroceryListDto groceryListDto);

}
