package com.tasks.ecommerceapp.data.model.customer.orders

import com.google.gson.annotations.SerializedName

data class DeliveryAddress(

    @SerializedName("country") var country: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("postal") var postal: String? = null

)