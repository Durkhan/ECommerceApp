package com.tasks.ecommerceapp.data.model.customer.orders

import com.google.gson.annotations.SerializedName
import com.tasks.ecommerceapp.data.model.customer.product.Customer
import com.tasks.ecommerceapp.data.model.customer.product.Product

data class GetAllOrdersResponse(

    @SerializedName("products") var products: List<Product> = listOf(),
    @SerializedName("canceled") var canceled: Boolean? = null,
    @SerializedName("_id") var id: String? = null,
    @SerializedName("deliveryAddress") var deliveryAddress: DeliveryAddress? = DeliveryAddress(),
    @SerializedName("letterSubject") var letterSubject: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("letterHtml") var letterHtml: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("customerId") var customerId: Customer?=Customer(),
    @SerializedName("orderNo") var orderNo: String? = null,
    @SerializedName("totalSum") var totalSum: Int? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("__v") var _v: Int? = null

)