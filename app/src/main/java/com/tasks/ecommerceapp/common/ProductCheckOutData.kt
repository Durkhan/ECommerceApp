package com.tasks.ecommerceapp.common

data class ProductCheckOutData(
    val name: String,
    val price: Double,
    val imageUrl:String,
    val productId:String,
    var selected: Boolean = false,
    var count: Int = 1
)