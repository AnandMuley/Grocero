package com.grocero.common

import com.grocero.beans.AbstractAuthenticationBean
import com.grocero.beans.CustomerBean
import com.grocero.beans.GroceryListBean
import com.grocero.beans.MasterListBean
import com.grocero.beans.ProductBean
import com.grocero.builders.GroceryListBeanBuilder
import com.grocero.builders.ProductBeanBuilder
import com.grocero.dtos.AuthenticationDto
import com.grocero.dtos.CustomerDto
import com.grocero.dtos.GroceryListDto
import com.grocero.dtos.MasterListDto
import com.grocero.dtos.ProductDto
import com.grocero.shared.SharedSpecification
import spock.lang.Subject

class BeanToDtoMapperSpec extends SharedSpecification {

    @Subject
    BeanToDtoMapper beanToDtoMapper

    def setup() {
        beanToDtoMapper = new BeanToDtoMapper(
                context: mockApplicationContext
        )
    }

    def "map - should map authentication details"() {
        given: "customer bean with all details"
        def authToken = "token-abd-cde-133"
        def password = "johndoe123"
        def username = "johndoe"
        AbstractAuthenticationBean customerBean = new CustomerBean(id: randomId, name: JOHN, username: username,
                password: password, authToken: authToken, role: UserRoles.ADMIN)

        1 * mockApplicationContext.getBean("authenticationDto",AuthenticationDto) >> new CustomerDto()

        when: "mapper is called"
        AuthenticationDto actual = beanToDtoMapper.map(customerBean as AbstractAuthenticationBean)

        then: "all details are mapped"
        actual.authToken == authToken
        actual.role == UserRoles.ADMIN
        actual.username == username
    }

    def "map - should map customer bean to dto"() {
        given: "customer bean with required details"
        def authToken = "token-abd-cde-133"
        def password = "johndoe123"
        def username = "johndoe"
        CustomerBean customerBean = new CustomerBean(id: randomId, name: JOHN, username: username,
                password: password, authToken: authToken)
        1 * mockApplicationContext.getBean(CustomerDto, randomId, JOHN) >> new CustomerDto(id: randomId, name: JOHN)

        when: "mapping function is called"
        CustomerDto actual = beanToDtoMapper.map(customerBean)

        then: "required details should be mapped"
        actual.id == customerBean.id
        actual.name == customerBean.name
        actual.username == username
        actual.password == null
        actual.authToken == authToken
    }

    def "map - should map master list bean to dto"() {
        given: "master list bean with required details"
        def customerId = "CID200"
        MasterListBean masterListBean = new MasterListBean(id: randomId, name: MASTER_LIST_NAME, customerId: customerId)
        masterListBean.items.addAll(["Apple", "Tomato"])
        1 * mockApplicationContext.getBean(MasterListDto, randomId, customerId, MASTER_LIST_NAME) >> new MasterListDto(id: randomId, customerId: customerId, name: MASTER_LIST_NAME)

        when: "mapping function is called"
        MasterListDto actual = beanToDtoMapper.map(masterListBean)

        then: "required details should be mapped"
        actual.id == masterListBean.id
        actual.name == masterListBean.name
        actual.customerId == masterListBean.customerId
        actual.items == masterListBean.items
    }

    def "map - should create product dto from bean"() {
        given: "details to be sent in response"
        ProductBean expected = new ProductBeanBuilder()
                .id(UUID.randomUUID().toString())
                .name("Apple").measuredIn("Kilo").quantity(2)
                .cost(200.50d).build()
        1 * mockApplicationContext.getBean(ProductDto.Builder) >> new ProductDto.Builder()

        when: "create is called"
        ProductDto actual = beanToDtoMapper.map(expected)

        then: "all details are populated"
        actual.id == expected.id
        actual.name == expected.name
        actual.measuredIn == expected.measuredIn
        actual.quantity == expected.quantity
        actual.cost == expected.cost
    }

    def "map - should create grocery list dto from bean"() {
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
        GroceryListDto actual = beanToDtoMapper.map(expected)

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
