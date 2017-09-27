package com.grocero.builders

import com.grocero.beans.ProductBean
import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy

@Builder(builderStrategy = ExternalStrategy, forClass = ProductBean)
class ProductBeanBuilder {
}
