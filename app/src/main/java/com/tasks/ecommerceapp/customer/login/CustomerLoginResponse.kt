package com.tasks.ecommerceapp.customer.login

import com.google.gson.annotations.SerializedName

data class CustomerLoginResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("token")
	val token: String? = null
)
