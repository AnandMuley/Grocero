package com.grocero.services.impl;

import com.grocero.beans.ProductBean;
import com.grocero.dtos.ProductDto;
import com.grocero.exceptions.NoDataFoundException;
import com.grocero.repositories.ProductRepository;
import com.grocero.services.IProductService;
import com.grocero.utils.DtoCreatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DtoCreatorUtil dtoCreatorUtil;

    @Autowired
    private ApplicationContext context;

    @Override
    public void add(ProductDto productDto) {
        ProductBean productBean = context.getBean(ProductBean.class);
        productBean.setMeasuredIn(productDto.getMeasuredIn());
        productBean.setName(productDto.getName());
        productBean.setQuantity(productDto.getQuantity());
        productBean.setCost(productDto.getCost());
        productRepository.save(productBean);
        productDto.setId(productBean.getId());
    }

    @Override
    public List<ProductDto> getAll() throws NoDataFoundException {
        List<ProductBean> productBeans = productRepository.findAll();
        if (CollectionUtils.isEmpty(productBeans)) {
            throw new NoDataFoundException("No products found");
        }
        return productBeans.stream().map(dtoCreatorUtil::createProductDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto findByName(String name) {
        ProductBean productBean = productRepository.findByName(name);
        return dtoCreatorUtil.createProductDto(productBean);
    }

    @Override
    public void update(ProductDto productDto) {
        ProductBean productBean = context.getBean(ProductBean.class, productDto.getId(),
                productDto.getName(), productDto.getMeasuredIn());
        productRepository.save(productBean);
    }

}
