package com.grocero.services

import com.grocero.beans.ProductBean
import com.grocero.dtos.ProductDto
import com.grocero.exceptions.NoDataFoundException
import com.grocero.repositories.ProductRepository
import com.grocero.shared.SharedSpecification
import com.grocero.utils.DtoCreatorUtil
import org.springframework.context.ApplicationContext
import spock.lang.Shared

class ProductServiceSpec extends SharedSpecification {

    public static final productName = "Apple"
    public static final measuredIn = "Kilos"
    public static final productId = UUID.randomUUID().toString()

    @Shared
    ProductService productService

    ProductRepository mockProductRepository

    ApplicationContext mockApplicationContext

    DtoCreatorUtil mockDtoCreatorUtil

    def setup() {
        mockApplicationContext = Mock(ApplicationContext)
        mockProductRepository = Mock(ProductRepository)
        mockDtoCreatorUtil = Mock(DtoCreatorUtil)
        productService = new ProductService(
                productRepository: mockProductRepository,
                context: mockApplicationContext,
                dtoCreatorUtil: mockDtoCreatorUtil
        )
    }

    def "add - should add product"() {
        given: "product to be added"
        ProductDto productDto = new ProductDto(name: productName, measuredIn: measuredIn, quantity: 1, cost: 20)
        1 * mockApplicationContext.getBean(ProductBean) >> new ProductBean()
        1 * mockProductRepository.save({ ProductBean it ->
            assert it.name == productName
            assert it.measuredIn == measuredIn
            assert it.quantity == 1
            assert it.cost == 20
            it.id = UUID.randomUUID().toString()
            true
        })

        when: "add method is called"
        productService.add(productDto)

        then: "id field should be populated"
        assert productDto.id != null

    }

    def "getAll - should return return the products"() {
        given: "database contains the list of products"
        1 * mockProductRepository.findAll() >> [new ProductBean(id: productId, name: productName, measuredIn: measuredIn)]
        1 * mockDtoCreatorUtil.createProductDto({ ProductBean bean ->
            assert bean.id == productId
            true
        }) >> new ProductDto(id: productId, name: productName, measuredIn: measuredIn)

        when: "getAll is called"
        List<ProductDto> actual = productService.getAll()

        then: "products are sent in the response"
        assert actual.size() == 1
        ProductDto actualProd = actual[0]
        assert actualProd.measuredIn == measuredIn
        assert actualProd.id == productId
        assert actualProd.name == productName
    }

    def "getAll - should throw an exception if no data found"() {
        given: "does not contain the products"
        1 * mockProductRepository.findAll() >> []

        when: "getAll is called"
        productService.getAll()

        then: "products are sent in the response"
        def ex = thrown(NoDataFoundException)
        assert ex.message == "No products found"
    }

    def "findByName - should return a matching product by name"() {
        given: "name of the product to find"
        ProductBean expected = new ProductBean(id: productId, name: productName, measuredIn: measuredIn)
        1 * mockProductRepository.findByName(productName) >> expected
        1 * mockDtoCreatorUtil.createProductDto({ ProductBean it ->
            assert it.id == productId
            true
        }) >> new ProductDto(id: productId, name: productName, measuredIn: measuredIn)

        when: "findByName is called"
        ProductDto actual = productService.findByName(productName)

        then: "matching product should be returned"
        assert actual.id == expected.id
        assert actual.measuredIn == expected.measuredIn
        assert actual.name == expected.name

    }

    def "update - should update the product details"() {
        given: "product details to be updated"
        ProductDto productDto = new ProductDto(id: productId, name: productName, measuredIn: measuredIn)
        1 * mockApplicationContext.getBean(ProductBean,productId,productName,measuredIn) >> new ProductBean(
                id:productId,name: productName,measuredIn: measuredIn)

        when: "update is called"
        productService.update(productDto)

        then: "product details are updated"
        1 * mockProductRepository.save({ProductBean bean ->
            assert bean.id == productId
            assert bean.name == productName
            assert bean.measuredIn == measuredIn
            true
        })
    }
}
