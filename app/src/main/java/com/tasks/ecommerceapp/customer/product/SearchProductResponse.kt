package com.tasks.ecommerceapp.customer.product

import com.google.gson.annotations.SerializedName


data class SearchProductResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("currentPrice")
	val currentPrice: Double? = null,

	@field:SerializedName("myCustomParam")
	val myCustomParam: String? = null,

	@field:SerializedName("itemNo")
	val itemNo: String? = null,

	@field:SerializedName("enabled")
	val enabled: Boolean? = null,

	@field:SerializedName("imageUrls")
	val imageUrls: List<String?>? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("previousPrice")
	val previousPrice: Double? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("categories")
	val categories: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("productUrl")
	val productUrl: String? = null,

	@field:SerializedName("brand")
	val brand: String? = null,

	@field:SerializedName("oneMoreCustomParam")
	val oneMoreCustomParam: OneMoreCustomParam? = null
)

data class OneMoreCustomParam(

	@field:SerializedName("rate")
	val rate: Any? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("likes")
	val likes: Int? = null
)

