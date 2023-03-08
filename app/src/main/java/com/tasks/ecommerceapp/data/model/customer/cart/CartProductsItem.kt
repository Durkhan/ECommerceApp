package com.tasks.ecommerceapp.data.model.customer.cart

import com.google.gson.annotations.SerializedName

data class CartProductsItem(

	@field:SerializedName("product")
	val product: CartProductResponse? = null,

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("cartQuantity")
	val cartQuantity: Int? = null
)
