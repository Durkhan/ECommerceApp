package com.tasks.ecommerceapp.customer.chagepassword

data class ChangePasswordResponse(
    val message: String? = null,
    val customer: Customer? = null
)

data class Customer(
    val date: String? = null,
    val lastName: String? = null,
    val gender: String? = null,
    val avatarUrl: String? = null,
    val telephone: String? = null,
    val isAdmin: Boolean? = null,
    val login: String? = null,
    val enabled: Boolean? = null,
    val firstName: String? = null,
    val password: String? = null,
    val v: Int? = null,
    val id: String? = null,
    val email: String? = null
)