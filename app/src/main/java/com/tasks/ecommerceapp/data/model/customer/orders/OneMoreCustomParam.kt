package com.tasks.ecommerceapp.data.model.customer.orders

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OneMoreCustomParam(

    @field:SerializedName("rate")
    val rate: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("likes")
    val likes: Int? = null
):Parcelable