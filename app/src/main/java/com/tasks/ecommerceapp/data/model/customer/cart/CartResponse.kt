package com.tasks.ecommerceapp.data.model.customer.cart

import com.google.gson.annotations.SerializedName

data class CartResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("customerId")
	val customerId: CustomerId? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("products")
	val products: List<CartProductsItem?>? = null
)








