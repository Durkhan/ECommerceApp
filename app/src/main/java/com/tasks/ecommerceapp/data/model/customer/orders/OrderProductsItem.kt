package com.tasks.ecommerceapp.data.model.customer.orders

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class OrderProductsItem(

	@field:SerializedName("product")
	val product: Product? = null,

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("cartQuantity")
	val cartQuantity: Int? = null
):Parcelable