package com.tasks.ecommerceapp.common

import com.tasks.ecommerceapp.data.model.customer.cart.CartProductsItem

data class ProductCheckOutData(
    var product:CartProductsItem,
    var selected: Boolean = false,
    var count: Int = 1
)