package com.grocero.builders

import com.grocero.beans.GroceryListBean
import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy

@Builder(builderStrategy = ExternalStrategy, forClass = GroceryListBean)
class GroceryListBeanBuilder {
}
