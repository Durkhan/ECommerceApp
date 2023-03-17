package com.tasks.ecommerceapp.data.model.customer.orders

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class OrdersResponse(

	@field:SerializedName("mailResult")
	val mailResult: MailResult? = null,

	@field:SerializedName("order")
	val order: Order? = null
)

@Parcelize
data class Product(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("currentPrice")
	val currentPrice: Int? = null,

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
	val previousPrice: Int? = null,

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("categories")
	val categories: String? = null,

	@field:SerializedName("productUrl")
	val productUrl: String? = null,

	@field:SerializedName("brand")
	val brand: String? = null,

	@field:SerializedName("oneMoreCustomParam")
	val oneMoreCustomParam: OneMoreCustomParam? = null
):Parcelable

data class MailResult(

	@field:SerializedName("envelopeTime")
	val envelopeTime: Int? = null,

	@field:SerializedName("messageTime")
	val messageTime: Int? = null,

	@field:SerializedName("envelope")
	val envelope: Envelope? = null,

	@field:SerializedName("rejected")
	val rejected: List<Any?>? = null,

	@field:SerializedName("response")
	val response: String? = null,

	@field:SerializedName("accepted")
	val accepted: List<String?>? = null,

	@field:SerializedName("messageId")
	val messageId: String? = null,

	@field:SerializedName("messageSize")
	val messageSize: Int? = null
)

data class Envelope(

	@field:SerializedName("from")
	val from: String? = null,

	@field:SerializedName("to")
	val to: List<String?>? = null
)



data class DeliveryAddress(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("postal")
	val postal: String? = null
)


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


