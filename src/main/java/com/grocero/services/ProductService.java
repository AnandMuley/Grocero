package com.grocero.services;

import com.grocero.beans.ProductBean;
import com.grocero.dtos.ProductDto;
import com.grocero.exceptions.NoDataFoundException;
import com.grocero.repositories.ProductRepository;
import com.grocero.utils.DtoCreatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    @Autowired
    private ApplicationContext context;

    public void add(ProductDto productDto) {
        ProductBean productBean = context.getBean(ProductBean.class);
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

    public ProductDto findByName(String name) {
        ProductBean productBean = productRepository.findByName(name);
        return dtoCreatorUtil.createProductDto(productBean);
    }

    public void update(ProductDto productDto) {
        ProductBean productBean = context.getBean(ProductBean.class, productDto.getId(),
                productDto.getName(), productDto.getMeasuredIn());
        productRepository.save(productBean);
    }

}
