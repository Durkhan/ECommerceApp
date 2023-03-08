package com.tasks.ecommerceapp.data.model.customer.product


import com.google.gson.annotations.SerializedName

data class ProductFilterResponse(

	@field:SerializedName("productsQuantity")
	val productsQuantity: Int? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem>?= null
)

