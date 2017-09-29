package com.grocero.services

import com.grocero.beans.CustomerBean
import com.grocero.beans.GroceryListBean
import com.grocero.beans.ProductBean
import com.grocero.builders.GroceryListBeanBuilder
import com.grocero.builders.GroceryListDtoBuilder
import com.grocero.builders.ProductDtoBuilder
import com.grocero.common.BeanToDtoMapper
import com.grocero.common.DtoToBeanMapper
import com.grocero.dtos.GroceryListDto
import com.grocero.dtos.ProductDto
import com.grocero.exceptions.NoDataFoundException
import com.grocero.repositories.GroceryListRepository
import com.grocero.services.impl.GroceryListServiceImpl
import com.grocero.shared.SharedSpecification
import spock.lang.Subject

import java.util.function.Function

class GroceryListServiceSpec extends SharedSpecification {

    @Subject
    GroceryListService groceryListService

    GroceryListRepository mockGroceryListRepository

    DtoToBeanMapper mockDtoToBeanMapper

    BeanToDtoMapper mockBeanToDtoMapper

    def setup() {
        mockDtoToBeanMapper = Mock(DtoToBeanMapper)
        mockGroceryListRepository = Mock(GroceryListRepository)
        mockBeanToDtoMapper = Mock(BeanToDtoMapper)
        groceryListService = new GroceryListServiceImpl(
                dtoToBeanMapper: mockDtoToBeanMapper,
                groceryListRepository: mockGroceryListRepository,
                beanToDtoMapper: mockBeanToDtoMapper
        )
    }

    def "update - should update the grocery list record"() {
        given: "grocery list dto with details to be updated"
        def listId = UUID.randomUUID().toString()
        GroceryListDto groceryListDto = new GroceryListDtoBuilder().id(listId).name("General Purchase")
                .items([new ProductDtoBuilder().build()]).build()
        1 * mockDtoToBeanMapper.map(groceryListDto) >> new GroceryListBean("General Purchase")

        when: "update is called"
        groceryListService.update(groceryListDto)

        then: "details should be updated"
        1 * mockGroceryListRepository.save({ GroceryListBean bean ->
            assert bean.name == groceryListDto.name
            assert bean.id == listId
            assert bean.items.size() == 0
            true
        })
    }

    def "findById - should find a grocery list by id"() {
        given: "listId"
        def listId = "LID101"
        GroceryListBean expected = new GroceryListBeanBuilder().items([]).build()
        1 * mockGroceryListRepository.findOne(listId) >> expected
        1 * mockBeanToDtoMapper.map(expected) >> new GroceryListDtoBuilder().items([]).build()

        when: "findById is called"
        Optional<GroceryListDto> actualOpt = groceryListService.findById(listId)

        then: "all details should be populated"
        GroceryListDto actual = actualOpt.get()
        actual.id == expected.id
        actual.name == expected.name
        actual.items.size() == expected.items.size()

    }

    def "findById - should return an empty optional if no db record found"() {
        given: "listId"
        def listId = "LID101"
        GroceryListBean expected = new GroceryListBeanBuilder().build()
        1 * mockGroceryListRepository.findOne(listId) >> null

        when: "findById is called"
        Optional<GroceryListDto> actualOpt = groceryListService.findById(listId)

        then: "all details should be populated"
        actualOpt.present == false

    }

    def "fetchAll - should fetch all customers"() {
        given: "all grocery lists are saved"
        List<GroceryListBean> groceryListBeans = [new GroceryListBeanBuilder().build()]
        1 * mockGroceryListRepository.findAll() >> groceryListBeans
        1 * mockBeanToDtoMapper.map(groceryListBeans[0]) >> new GroceryListDtoBuilder().build()

        when: "fetchAll is called"
        List<GroceryListDto> groceryListDtoList = groceryListService.fetchAll()

        then: "all details are sent"
        groceryListDtoList.size() == 1
    }

    def "fetchAll - should throw an exception if no grocery list found"() {
        given: "no grocery lists in db"
        1 * mockGroceryListRepository.findAll() >> groceryListBeans

        when: "fetchAll is called"
        groceryListService.fetchAll()

        then: "all details are sent"
        def ex = thrown(NoDataFoundException)
        ex.message == null

        where: "possible scenarios are"
        groceryListBeans | _
        null             | _
        []               | _
    }

    def "delete - should delete a customer record"() {
        given: "listId of the list to be deleted"
        def listId = "LID201"

        when: "delete operation is performed"
        groceryListService.delete(listId)

        then: "list is deleted successfully"
        1 * mockGroceryListRepository.delete(listId)
    }

    def "create - should save the list in the DB"() {
        given: "details to be saved"
        def listName = "General List"
        GroceryListDto dto = new GroceryListDtoBuilder().name(listName).build()
        GroceryListBean bean = new GroceryListBeanBuilder().name(listName).build()

        1 * mockDtoToBeanMapper.map(dto,{Function fn ->
            // TODO need to investigate how to validate argument which is a lambda function
            assert fn instanceof Function<ProductDto,CustomerBean>
            true
        }) >> bean
        1 * mockGroceryListRepository.save({GroceryListBean it->
            assert it.id == null
            assert it.name == listName
            assert it.items == null
            it.id = randomId
            true
        })


        when: "create is invoked"
        groceryListService.create(dto)

        then: "details are persisted"
        dto.id == randomId
    }

}
