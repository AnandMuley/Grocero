package com.grocero.resources

import com.grocero.common.ApplicationContextTestUtils
import com.grocero.common.JerseySpec
import com.grocero.dtos.CustomerDto
import com.grocero.services.CustomerService
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.springframework.test.util.ReflectionTestUtils

import javax.ws.rs.core.GenericType

import static org.mockito.ArgumentMatchers.argThat
import static org.mockito.Mockito.doNothing
import static org.mockito.Mockito.doThrow
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

class CustomerResourceSpec extends JerseySpec {

    CustomerService mockCustomerService

    @Before
    void setup() {
        mockCustomerService = mock(CustomerService)
        CustomerResource customerResource = ApplicationContextTestUtils.getApplicationContext().getBean(CustomerResource)
        ReflectionTestUtils.setField(customerResource, "customerService", mockCustomerService)
    }

    @Test
    void "save"() {
        def response = target('customers').request().post(new CustomerDto(),GenericType)
        doNothing().when(mockCustomerService).save()
        response == 'Hello World!'

        verify(mockCustomerService).save(argThat({ CustomerDto dto -> dto.id == 1 }))
    }
}
