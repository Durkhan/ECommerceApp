package com.tasks.ecommerceapp.data.model.customer.orders

import com.google.gson.annotations.SerializedName

data class OneMoreCustomParam(

    @field:SerializedName("rate")
    val rate: Any? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("likes")
    val likes: Int? = null
)