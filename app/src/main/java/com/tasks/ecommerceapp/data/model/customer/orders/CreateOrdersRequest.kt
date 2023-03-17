package com.tasks.ecommerceapp.data.model.customer.orders


import com.squareup.moshi.JsonClass
import com.tasks.ecommerceapp.data.model.customer.cart.CartProductsItem
import com.tasks.ecommerceapp.data.model.customer.cart.CustomerId
import org.bson.types.ObjectId

@JsonClass(generateAdapter = true)
data class CreateOrdersRequest(
    val customerId: String,
    val email: String,
    val mobile: String,
    val products: List<CartProductsItem>,
    val letterSubject:String= "Thank you for order! You are welcome!",
    val letterHtml: String="Your order is placed. OrderNo is 023689452.Other details about order in your",
)