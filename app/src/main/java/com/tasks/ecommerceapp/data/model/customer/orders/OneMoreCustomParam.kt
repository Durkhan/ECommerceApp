package com.tasks.ecommerceapp.data.model.customer.orders

import com.google.gson.annotations.SerializedName

data class OneMoreCustomParam(

    @SerializedName("description") var description: String? = null,
    @SerializedName("rate") var rate: Double? = null,
    @SerializedName("likes") var likes: Int? = null

)