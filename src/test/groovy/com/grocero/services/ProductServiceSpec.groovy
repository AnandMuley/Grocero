package com.grocero.services

import com.grocero.beans.ProductBean
import com.grocero.dtos.ProductDto
import com.grocero.repositories.ProductRepository
import com.grocero.shared.SharedSpecification
import spock.lang.Shared

class ProductServiceSpec extends SharedSpecification{

    @Shared
    ProductService productService

    ProductRepository mockProductRepository

    def setup(){
        mockProductRepository = Mock(ProductRepository)
        productService = new ProductService(productRepository: mockProductRepository)
    }

    def "add - should add product"(){
        given:
        ProductDto productDto = new ProductDto(name: "Apple",measuredIn: "Kilos",quantity: 1,cost: 20)
        1 * mockProductRepository.save({ProductBean it->
            assert it.name == "Apple"
            assert it.measuredIn == "Kilos"
            assert it.quantity == 1
            assert it.cost == 20
            it.id = UUID.randomUUID().toString()
            true
        })

        when:
        productService.add(productDto)

        then:
        assert productDto.id != null

    }

}
