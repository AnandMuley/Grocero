package com.grocero.builders

import com.grocero.beans.CustomerBean
import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy

@Builder(builderStrategy = ExternalStrategy,forClass = CustomerBean)
class CustomerBeanBuilder {
}
