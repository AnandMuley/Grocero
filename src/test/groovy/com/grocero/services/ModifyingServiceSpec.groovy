package com.grocero.services

import com.grocero.beans.CustomerBean
import com.grocero.beans.MasterListBean
import com.grocero.common.DtoToBeanMapper
import com.grocero.dtos.CustomerDto
import com.grocero.dtos.MasterListDto
import com.grocero.repositories.CustomerRepository
import com.grocero.repositories.MasterListRepository
import com.grocero.shared.SharedSpecification
import spock.lang.Subject

class ModifyingServiceSpec extends SharedSpecification {

    @Subject
    ModifyingService modifyingService

    MasterListRepository mockMasterListRepository
    DtoToBeanMapper mockDtoToBeanMapper
    CustomerRepository mockCustomerRepository

    def setup() {
        mockCustomerRepository = Mock(CustomerRepository)
        mockMasterListRepository = Mock(MasterListRepository)
        mockDtoToBeanMapper = Mock(DtoToBeanMapper)
        modifyingService = new ModifyingService(
                masterListRepository: mockMasterListRepository,
                dtoToBeanMapper: mockDtoToBeanMapper,
                customerRepository: mockCustomerRepository
        )
    }

    def "save - should save the masterlist in the database"() {
        given: "details to be saved"
        MasterListDto masterListDto = new MasterListDto(randomId, "John")
        MasterListBean masterListBean = new MasterListBean(id: randomId)
        1 * mockDtoToBeanMapper.map(masterListDto) >> masterListBean
        1 * mockMasterListRepository.save({ MasterListBean it ->
            assert it.id == randomId
            assert it.customerId == null
            assert it.name == null
            true
        }) >> masterListBean

        when: "save operation is performed"
        modifyingService.save(masterListDto)

        then: "details should be saved and the id property should be populated"
        assert masterListDto.id == randomId
    }


}
