package com.tasks.ecommerceapp.data.model.customer.orders


data class UpdateOrderRequest(
    var email:String,
    var status:String,
    var letterHtml:String="Your order completed",
    var letterSubject: String="Your Order"
)
