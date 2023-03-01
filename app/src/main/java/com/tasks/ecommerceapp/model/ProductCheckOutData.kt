package com.tasks.ecommerceapp.model

data class ProductCheckOutData(
    val name: String,
    val price: Double,
    var selected: Boolean = false,
    var count: Int = 1
)