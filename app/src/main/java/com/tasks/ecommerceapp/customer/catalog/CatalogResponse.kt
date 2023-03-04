package com.tasks.ecommerceapp.customer.catalog

import com.google.gson.annotations.SerializedName

data class CatalogResponse(
	@field:SerializedName("imgUrl")
	val imgUrl: String? = null,

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("level")
	val level: Int? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("parentId")
	val parentId: String? = null
)


