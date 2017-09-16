package com.grocero.services;

import com.grocero.dtos.ProductDto;
import com.grocero.exceptions.NoDataFoundException;

import java.util.List;

/**
 * Created by andriox on 16/9/17.
 */
public interface IProductService {
    void add(ProductDto productDto);

    List<ProductDto> getAll() throws NoDataFoundException;

    ProductDto findByName(String name);

    void update(ProductDto productDto);
}
