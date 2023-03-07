package com.tasks.ecommerceapp.data.model.customer.login

data class CustomerLoginRequest (
    val loginOrEmail: String,
    val password: String
)