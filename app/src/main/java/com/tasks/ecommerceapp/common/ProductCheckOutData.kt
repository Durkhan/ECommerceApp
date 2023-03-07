package com.tasks.ecommerceapp.common

data class ProductCheckOutData(
    val name: String,
    val price: Double,
    var selected: Boolean = false,
    var count: Int = 1
)