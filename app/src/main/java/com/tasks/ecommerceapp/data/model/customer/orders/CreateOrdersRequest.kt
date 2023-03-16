package com.tasks.ecommerceapp.data.model.customer.orders

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.bson.types.ObjectId
import java.util.*

@JsonClass(generateAdapter = true)
data class CreateOrdersRequest(
    var customerId: ObjectId? = null,
    val email: String,
    val mobile: String,
    val letterSubject: String?=null,
    val letterHtml: String? =null,
    @Json(name = "deliveryAddress") var deliveryAddress: DeliveryAddress? = null,
    var shipping: String? = null,
    var paymentInfo: String? = null,
    var status: String? = null,
    val date: Date? = null
)