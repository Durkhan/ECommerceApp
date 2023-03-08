package com.tasks.ecommerceapp.data.model.customer.product

import com.google.gson.annotations.SerializedName

data class ReviewsForProductResponse(
    @SerializedName("_id") var id: String? = null,
    @SerializedName("customer") var customer: Customer? = Customer(),
    @SerializedName("product") var product: Product? = Product(),
    @SerializedName("content") var content: String? = null,
    @SerializedName("__v") var _v: Int? = null
)