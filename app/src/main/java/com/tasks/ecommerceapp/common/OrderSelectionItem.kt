package com.tasks.ecommerceapp.common

data class OrderSelectionItem(
    val selected: Boolean = false,
    val status: String,
    val drawable:Int,
    val text:String
)