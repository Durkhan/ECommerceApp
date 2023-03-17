package com.tasks.ecommerceapp.data.model.customer.review

data class OrderReviewRequest(
    val customerId:String,
    val product: String? = null,
    val content: String? = null,
    val imgUrls: List<String>? = null
)