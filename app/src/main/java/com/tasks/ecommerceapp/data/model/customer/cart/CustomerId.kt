package com.tasks.ecommerceapp.data.model.customer.cart

import com.google.gson.annotations.SerializedName

data class CustomerId(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("avatarUrl")
	val avatarUrl: String? = null,

	@field:SerializedName("telephone")
	val telephone: String? = null,

	@field:SerializedName("isAdmin")
	val isAdmin: Boolean? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("enabled")
	val enabled: Boolean? = null,

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)