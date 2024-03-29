package com.tasks.ecommerceapp.data.model.customer.register

data class CustomerResponse(
    val email: String? = null,
    val date: String? = null,
    val lastName: String? = null,
    val gender: String? = null,
    val avatarUrl: String? = null,
    val telephone: String? = null,
    val isAdmin: Boolean? = null,
    val login: String? = null,
    val firstName: String? = null,
    val password: String? = null,
    val token:String?=null,
    val _id:String?=null
)