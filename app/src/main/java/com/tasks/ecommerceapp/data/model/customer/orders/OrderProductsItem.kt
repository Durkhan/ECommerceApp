package com.tasks.ecommerceapp.data.model.customer.orders

import com.google.gson.annotations.SerializedName

data class OrderProductsItem(

	@field:SerializedName("product")
	val product: Product? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("cartQuantity")
	val cartQuantity: Int? = null
)