package com.tasks.ecommerceapp.data.model.customer.review

import com.google.gson.annotations.SerializedName


data class ProductReviewResponse(

    @field:SerializedName("product")
    val product: Product? = null,

    @field:SerializedName("__v")
    val v: Int? = null,

    @field:SerializedName("_id")
    val _id: String? = null,

    @field:SerializedName("content")
    val content: String? = null,

    @field:SerializedName("customer")
    val customer: CustomerReviewResponse? = null
)
