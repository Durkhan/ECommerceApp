package com.tasks.ecommerceapp.data.model.customer.cart

import com.google.gson.annotations.SerializedName
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem

data class CartProductResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("color")
	val color: String? = null,

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
	val imageUrls: List<String>? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("categories")
	val categories: String? = null,

	@field:SerializedName("ram")
	val ram: String? = null,

	@field:SerializedName("myCustomParam")
	val myCustomParam: String? = null,

	@field:SerializedName("currentPrice")
	val currentPrice: Double? = null,

	@field:SerializedName("previousPrice")
	val previousPrice: Double? = null,

	@field:SerializedName("productUrl")
	val productUrl: String? = null,

	@field:SerializedName("brand")
	val brand: String? = null,

	@field:SerializedName("oneMoreCustomParam")
	val oneMoreCustomParam: OneMoreCustomParam? = null
)
fun ProductsItem.toRequest(): CartProductResponse {
	return CartProductResponse(
		date =date,
		quantity=quantity,
		color=color,
		currentPrice = currentPrice,
		previousPrice = previousPrice,
		someOtherFeature = someOtherFeature,
		weight = weight,
		itemNo = itemNo,
		enabled = enabled,
		size = size,
		imageUrls = imageUrls,
		v = v,
		name= name,
		_id = _id,
		ram = ram
	)
}