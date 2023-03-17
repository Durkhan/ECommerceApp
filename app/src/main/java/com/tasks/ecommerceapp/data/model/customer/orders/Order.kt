package com.tasks.ecommerceapp.data.model.customer.orders

import com.google.gson.annotations.SerializedName


data class Order(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("orderNo")
	val orderNo: String? = null,

	@field:SerializedName("mobile")
	val mobile: String? = null,

	@field:SerializedName("letterHtml")
	val letterHtml: String? = null,

	@field:SerializedName("letterSubject")
	val letterSubject: String? = null,

	@field:SerializedName("products")
	val products: List<OrderProductsItem>? = null,

	@field:SerializedName("totalSum")
	val totalSum: Int? = null,

	@field:SerializedName("canceled")
	val canceled: Boolean? = null,

	@field:SerializedName("shipping")
	val shipping: String? = null,

	@field:SerializedName("deliveryAddress")
	val deliveryAddress: DeliveryAddress? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("customerId")
	val customerId: CustomerId? = null,

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("paymentInfo")
	val paymentInfo: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)