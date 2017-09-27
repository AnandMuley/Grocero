package com.grocero.utils

import com.grocero.beans.GroceryListBean
import com.grocero.beans.ProductBean
import com.grocero.builders.GroceryListDtoBuilder
import com.grocero.builders.ProductDtoBuilder
import com.grocero.dtos.GroceryListDto
import com.grocero.dtos.ProductDto
import com.grocero.shared.SharedSpecification
import org.springframework.context.ApplicationContext
import spock.lang.Subject

class BeanCreatorUtilSpec extends SharedSpecification {

    @Subject
    BeanCreatorUtil beanCreatorUtil

    ApplicationContext mockApplicationContext

    def setup() {
        mockApplicationContext = Mock(ApplicationContext)
        beanCreatorUtil = new BeanCreatorUtil(
                context: mockApplicationContext
        )
    }

    def "create - should create productbean from dto"() {
        given: "product details in the dto"
        ProductDto expected = new ProductDtoBuilder().name("John")
                .quantity(10).measuredIn("Kg").cost(20.50d)
                .build()

        1 * mockApplicationContext.getBean(ProductBean.Builder) >> new ProductBean.Builder()

        when: "create is called"
        ProductBean actual = beanCreatorUtil.create(expected)

        then: "all details should be mapped"
        actual.id == expected.id
        actual.name == expected.name
        actual.measuredIn == expected.measuredIn
        actual.quantity == expected.quantity
        actual.cost == expected.cost
    }

    def "create - should create a grocery list bean from dto"() {
        given: "dto with required details to persist"
        ProductDto productDto = new ProductDtoBuilder().name("John")
                .quantity(10).measuredIn("Kg").cost(20.50d)
                .build()

        GroceryListDto expected = new GroceryListDtoBuilder().name("27-Sept-2017")
                .items([productDto]).build()

        1 * mockApplicationContext.getBean(GroceryListBean, expected.name) >> new GroceryListBean(expected.name)
        1 * mockApplicationContext.getBean(ProductBean.Builder) >> new ProductBean.Builder()

        when: "create is called"
        GroceryListBean actual = beanCreatorUtil.create(expected,beanCreatorUtil.&create)

        then: "all details should be populated"
        actual.name == expected.name
        actual.id == expected.id
        actual.items.size() == 1
        ProductBean actualBean = actual.items[0]
        actualBean.id == productDto.id
        actualBean.name == productDto.name
        actualBean.measuredIn == productDto.measuredIn
        actualBean.quantity == productDto.quantity
        actualBean.cost == productDto.cost
    }

    def "update - should populate the grocery list bean from the dto"() {
        given: "dto with required details to persist"
        ProductDto productDto = new ProductDtoBuilder().name("John")
                .quantity(10).measuredIn("Kg").cost(20.50d)
                .build()

        GroceryListDto expected = new GroceryListDtoBuilder().name("27-Sept-2017")
                .items([productDto]).build()

        ProductBean productBean = new ProductBean(
                name: productDto.name,
                measuredIn: productDto.measuredIn,
                quantity: productDto.quantity
        )

        1 * mockApplicationContext.getBean(GroceryListBean, expected.id, expected.name) >> new GroceryListBean(expected.id, expected.name)
        1 * mockApplicationContext.getBean(ProductBean.Builder) >> new ProductBean.Builder()

        when: "create is called"
        GroceryListBean actual = beanCreatorUtil.update(expected)

        then: "all details should be populated"
        actual.id == expected.id
        actual.name == expected.name
        actual.items.size() == 1

        ProductBean actualBean = actual.items[0]
        actualBean.id == productDto.id
        actualBean.cost == productDto.cost
        actualBean.name == productDto.name
        actualBean.measuredIn == productDto.measuredIn
        actualBean.quantity == productDto.quantity
    }

}
