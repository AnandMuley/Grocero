package com.grocero.resources

import com.grocero.builders.ProductDtoBuilder
import com.grocero.dtos.ProductDto
import com.grocero.exceptions.NoDataFoundException
import com.grocero.services.ProductService
import com.grocero.shared.SharedSpecification
import spock.lang.Subject

import javax.ws.rs.core.Response

class ProductResourceSpec extends SharedSpecification {

    @Subject
    ProductResource productResource

    ProductService mockProductService

    def setup() {
        mockProductService = Mock(ProductService)
        productResource = new ProductResource(mockProductService)
    }

    def "add - should add a product"() {
        given: "product to add"
        def productName = "Apple"
        def measuredIn = "Kilo"
        def productId = "PID200"
        ProductDto productDto = new ProductDtoBuilder()
                .name(productName).cost(200d).measuredIn(measuredIn)
                .quantity(12).build()

        1 * mockProductService.add({ ProductDto dto ->
            dto.id == null
            dto.name == productDto.name
            dto.measuredIn == productDto.measuredIn
            dto.cost == productDto.cost
            dto.quantity == productDto.quantity
            dto.id = productId
            true
        })

        when: "add is called"
        Response actualResponse = productResource.add(productDto)

        then: "details are sent in response"
        actualResponse.status == 200
        ProductDto actual = actualResponse.entity
        actual.id == productId
        actual.name == productDto.name
        actual.measuredIn == productDto.measuredIn
        actual.cost == productDto.cost
        actual.quantity == productDto.quantity
    }

    def "getAll - should fetch all products"() {
        given: "products are added in the DB"
        ProductDto expected = new ProductDtoBuilder().name("Apple").build()

        1 * mockProductService.getAll() >> [expected]

        when: "getAll is invoked"
        Response actualResponse = productResource.getAll()

        then: "all products should be sent"
        actualResponse.status == 200
        List<ProductDto> actualProds = actualResponse.entity
        actualProds.size() == 1
        ProductDto actual = actualProds[0]
        actual.id == expected.id
        actual.name == expected.name
        actual.measuredIn == expected.measuredIn
        actual.cost == expected.cost
        actual.quantity == expected.quantity
    }

    def "getAll - should return an error if not products found"() {
        given: "products are not added in DB"
        def expectedEx = new NoDataFoundException("No products found")

        1 * mockProductService.getAll() >> { throw expectedEx }

        when: "getAll is invoked"
        Response actualResponse = productResource.getAll()

        then: "all products should be sent"
        actualResponse.status == 204
        actualResponse.hasEntity() == false

    }

    def "updateProduct - should update product"() {
        given: "details of the product to be updated"
        def productId = generateRandomId()
        ProductDto expected = new ProductDtoBuilder().name("Apple").build()

        1 * mockProductService.update({ ProductDto dto ->
            assert dto.id == productId
            assert dto.name == expected.name
            assert dto.quantity == expected.quantity
            assert dto.cost == expected.cost
            true
        })

        when: "updateProduct is invoked"
        Response actualResp = productResource.updateProduct(productId, expected)

        then: "success response is sent"
        actualResp.status == 200
        actualResp.hasEntity() == false
    }

    def "getByName - should get the product by name"() {
        given: "productName to be searched"
        def productName = "Apple"
        ProductDto expected = new ProductDtoBuilder().build()

        1 * mockProductService.findByName(productName) >> expected

        when: "getByName is invoked"
        Response actualResp = productResource.getByName(productName)

        then: "all details are sent in response"
        actualResp.status == 200
        ProductDto actual = actualResp.entity
        actual.id == expected.id
        actual.name == expected.name
        actual.measuredIn == expected.measuredIn
        actual.quantity == expected.quantity
        actual.cost == expected.cost
    }

}
