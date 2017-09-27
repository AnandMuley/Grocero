package com.grocero.utils

import com.grocero.beans.GroceryListBean
import com.grocero.beans.ProductBean
import com.grocero.builders.GroceryListBeanBuilder
import com.grocero.builders.ProductBeanBuilder
import com.grocero.dtos.GroceryListDto
import com.grocero.dtos.ProductDto
import com.grocero.shared.SharedSpecification
import org.springframework.context.ApplicationContext
import spock.lang.Subject

class DtoCreatorUtilSpec extends SharedSpecification {

    @Subject
    DtoCreatorUtil dtoCreatorUtil

    ApplicationContext mockApplicationContext

    def setup() {
        mockApplicationContext = Mock(ApplicationContext)
        dtoCreatorUtil = new DtoCreatorUtil(
                context: mockApplicationContext
        )
    }

    def "create - should create product dto from bean"() {
        given: "details to be sent in response"
        ProductBean expected = new ProductBeanBuilder()
                .id(UUID.randomUUID().toString())
                .name("Apple").measuredIn("Kilo").quantity(2)
                .cost(200.50d).build()
        1 * mockApplicationContext.getBean(ProductDto.Builder) >> new ProductDto.Builder()

        when: "create is called"
        ProductDto actual = dtoCreatorUtil.create(expected)

        then: "all details are populated"
        actual.id == expected.id
        actual.name == expected.name
        actual.measuredIn == expected.measuredIn
        actual.quantity == expected.quantity
        actual.cost == expected.cost
    }

    def "create - should create grocery list dto from bean"() {
        given: "bean with all the details"
        ProductBean productBean = new ProductBeanBuilder()
                .id(UUID.randomUUID().toString())
                .name("Apple").measuredIn("Kilo").quantity(2)
                .cost(200.50d).build()
        GroceryListBean expected = new GroceryListBeanBuilder().id(UUID.randomUUID().toString())
                .name("General Purchase").items([productBean]).build()

        1 * mockApplicationContext.getBean(GroceryListDto) >> new GroceryListDto()
        1 * mockApplicationContext.getBean(ProductDto.Builder) >> new ProductDto.Builder()

        when: "create is called"
        GroceryListDto actual = dtoCreatorUtil.create(expected)

        then: "all details should be populated"
        actual.id == expected.id
        actual.name == expected.name
        actual.items.size() == 1
        ProductDto productDtoActual = actual.items[0]
        productDtoActual.id == productBean.id
        productDtoActual.name == productBean.name
        productDtoActual.measuredIn == productBean.measuredIn
        productDtoActual.quantity == productBean.quantity
        productDtoActual.cost == productBean.cost
    }

}
