package com.tasks.ecommerceapp.data.model.customer.review

import android.os.Parcelable
import com.tasks.ecommerceapp.data.model.customer.orders.OrderProductsItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderReview(
    val customerId:String,
    val product: String? = null,
    val productsItem: OrderProductsItem
):Parcelable