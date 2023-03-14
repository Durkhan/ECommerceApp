package com.tasks.ecommerceapp.data.model.customer.orders

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem

@JsonClass(generateAdapter = true)
data class ProductsItemRequest(
    val date: String? = null,
    val description: String? = null,
    val quantity: Int? = null,
    val color: String? = null,
    val currentPrice: Double? = null,
    val previousPrice: Double? = null,
    val someOtherFeature: String? = null,
    val weight: String? = null,
    val itemNo: String? = null,
    val enabled: Boolean? = null,
    val size: String? = null,
    val imageUrls: List<String>? = null,
    val v: Int? = null,
    val name: String? = null,
    val _id: String? = null,
    val categories: String? = null,
    val ram: String? = null
)
fun ProductsItem.toRequest():ProductsItemRequest{
    return ProductsItemRequest(
        date=date,
        description,
        quantity,
        color,
        currentPrice,
        previousPrice,
        someOtherFeature,
        weight,
        itemNo,
        enabled,
        size,
        imageUrls,
        v,
        name,
        _id,
        ram
    )
}