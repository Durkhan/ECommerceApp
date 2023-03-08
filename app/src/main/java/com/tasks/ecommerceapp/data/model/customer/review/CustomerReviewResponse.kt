package com.tasks.ecommerceapp.data.model.customer.review

import com.google.gson.annotations.SerializedName

data class CustomerReviewResponse(
    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("firstName")
    val firstName: String? = null,

    @field:SerializedName("lastName")
    val lastName: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("__v")
    val v: Int? = null,

    @field:SerializedName("isAdmin")
    val isAdmin: Boolean? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("enabled")
    val enabled: Boolean? = null,

    @field:SerializedName("email")
    val email: String? = null
)
