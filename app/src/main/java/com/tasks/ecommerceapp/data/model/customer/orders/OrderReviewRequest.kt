package com.tasks.ecommerceapp.data.model.customer.orders

data class OrderReviewRequest(
    val product: String? = null,
    val content: String? = null,
    val imgUrls: List<String>? = null
)