package com.tasks.ecommerceapp.data.model.customer.product

import com.google.gson.annotations.SerializedName

data class Customer(

    @SerializedName("isAdmin") var isAdmin: Boolean? = null,
    @SerializedName("enabled") var enabled: Boolean? = null,
    @SerializedName("_id") var id: String? = null,
    @SerializedName("firstName") var firstName: String? = null,
    @SerializedName("lastName") var lastName: String? = null,
    @SerializedName("login") var login: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("__v") var _v: Int? = null

)