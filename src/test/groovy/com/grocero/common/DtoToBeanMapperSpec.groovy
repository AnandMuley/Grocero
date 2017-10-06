package com.grocero.common

import com.grocero.beans.CustomerBean
import com.grocero.beans.GroceryListBean
import com.grocero.beans.MasterListBean
import com.grocero.beans.ProductBean
import com.grocero.builders.GroceryListBeanBuilder
import com.grocero.builders.GroceryListDtoBuilder
import com.grocero.builders.MasterListBeanBuilder
import com.grocero.builders.ProductBeanBuilder
import com.grocero.builders.ProductDtoBuilder
import com.grocero.dtos.CustomerDto
import com.grocero.dtos.GroceryListDto
import com.grocero.dtos.MasterListDto
import com.grocero.dtos.ProductDto
import com.grocero.shared.SharedSpecification
import org.springframework.context.ApplicationContext
import spock.lang.Subject

class DtoToBeanMapperSpec extends SharedSpecification {

    @Subject
    DtoToBeanMapper dtoToBeanMapper

    ApplicationContext mockApplicationContext

    def setup() {
        mockApplicationContext = Mock(ApplicationContext)
        dtoToBeanMapper = new DtoToBeanMapper(context: mockApplicationContext)
    }

    def "map - should map details from MasterList DTO to Bean by creating new"() {
        given: "dto to map"
        MasterListDto expected = new MasterListDto(randomId, randomId, MASTER_LIST_NAME)
        expected.items.addAll(["Apple", "Tomato"])
        1 * mockApplicationContext.getBean(MasterListBean) >> new MasterListBean()

        when: "mapping function is called"
        MasterListBean actual = dtoToBeanMapper.map(expected, Optional.empty())

        then: "required details should be mapped"
        assert actual.name == expected.name
        assert actual.customerId == expected.customerId
        assert actual.items == expected.items
    }

    def "map - should map details from MasterList DTO to given Bean"() {
        given: "dto to map"
        MasterListDto expected = new MasterListDto(randomId, randomId, MASTER_LIST_NAME)
        expected.items.addAll(["Apple", "Tomato", "Cabbage"])

        MasterListBean masterListBean = new MasterListBeanBuilder().id(randomId)
                .name(MASTER_LIST_NAME).customerId(randomId).items(["Cabbage"] as LinkedHashSet).build()

        when: "mapping function is called"
        MasterListBean actual = dtoToBeanMapper.map(expected, Optional.of(masterListBean))

        then: "required details should be mapped"
        assert actual.name == expected.name
        assert actual.customerId == expected.customerId
        assert actual.items == expected.items
    }

    def "map - should map details from customer dto to bean"() {
        given: "dto to map"
        CustomerDto customerDto = new CustomerDto(name: JOHN)
        1 * mockApplicationContext.getBean(CustomerBean) >> new CustomerBean()

        when: "mapping function is called"
        CustomerBean actual = dtoToBeanMapper.map(customerDto)

        then: "details should be mapped"
        actual.name == customerDto.name
        actual.id == customerDto.id
        actual.password == customerDto.password
        actual.role == customerDto.role
        actual.username == customerDto.username

    }


    def "map - should create productbean from dto"() {
        given: "product details in the dto"
        ProductDto expected = new ProductDtoBuilder().name("John")
                .quantity(10).measuredIn("Kg").cost(20.50d)
                .build()

        1 * mockApplicationContext.getBean(ProductBean.Builder) >> new ProductBean.Builder()

        when: "create is called"
        ProductBean actual = dtoToBeanMapper.map(expected)

        then: "all details should be mapped"
        actual.id == expected.id
        actual.name == expected.name
        actual.measuredIn == expected.measuredIn
        actual.quantity == expected.quantity
        actual.cost == expected.cost
    }

    def "map - should create a grocery list bean from dto"() {
        given: "dto with required details to persist"
        ProductDto productDto = new ProductDtoBuilder().name("John")
                .quantity(10).measuredIn("Kg").cost(20.50d)
                .build()

        GroceryListDto expected = new GroceryListDtoBuilder().name("27-Sept-2017")
                .items([productDto]).build()

        1 * mockApplicationContext.getBean(GroceryListBean, expected.name) >> new GroceryListBean(expected.name)
        1 * mockApplicationContext.getBean(ProductBean.Builder) >> new ProductBean.Builder()

        when: "create is called"
        GroceryListBean actual = dtoToBeanMapper.map(expected,dtoToBeanMapper.&map)

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

    def "map - should populate the grocery list bean from the dto"() {
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
        GroceryListBean actual = dtoToBeanMapper.map(expected)

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
