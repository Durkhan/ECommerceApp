package com.tasks.ecommerceapp.data.model.customer.product

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("currentPrice")
	val currentPrice: Double? = null,

	@field:SerializedName("previousPrice")
	val previousPrice: Double? = null,

	@field:SerializedName("someOtherFeature")
	val someOtherFeature: String? = null,

	@field:SerializedName("weight")
	val weight: String? = null,

	@field:SerializedName("itemNo")
	val itemNo: String? = null,

	@field:SerializedName("enabled")
	val enabled: Boolean? = null,

	@field:SerializedName("size")
	val size: String? = null,

	@field:SerializedName("imageUrls")
	val imageUrls: List<String?>? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("categories")
	val categories: String? = null,

	@field:SerializedName("ram")
	val ram: String? = null
)
