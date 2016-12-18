package com.grocero.services;

import com.grocero.beans.ProductBean;
import com.grocero.dtos.ProductDto;
import com.grocero.exceptions.NoDataFoundException;
import com.grocero.repositories.ProductRepository;
import com.grocero.utils.DtoCreatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DtoCreatorUtil dtoCreatorUtil;

    public void add(ProductDto productDto) {
        ProductBean productBean = new ProductBean();
        productBean.setMeasuredIn(productDto.getMeasuredIn());
        productBean.setName(productDto.getName());
        productBean.setQuantity(productDto.getQuantity());
        productBean.setCost(productDto.getCost());
        productRepository.save(productBean);
        productDto.setId(productBean.getId());
    }

    public List<ProductDto> getAll() throws NoDataFoundException {
        List<ProductBean> productBeans = productRepository.findAll();
        if (CollectionUtils.isEmpty(productBeans)) {
            throw new NoDataFoundException("No products found");
        }
        return productBeans.stream().map(dtoCreatorUtil::createProductDto).collect(Collectors.toList());
    }

    public ProductBean findByName(String name) {
        return productRepository.findByName(name);
    }

    public void update(ProductDto productDto) {
        ProductBean productBean = new ProductBean(productDto.getId(),
                productDto.getName(), productDto.getMeasuredIn());
        productRepository.save(productBean);
    }

}
