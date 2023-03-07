package com.tasks.ecommerceapp.data.model.customer.register

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerRegisterRequest(
    val email: String? = null,
    val date: String? = null,
    val lastName: String? = null,
    val gender: String? = null,
    val avatarUrl: String? = null,
    val telephone: String? = null,
    val isAdmin: Boolean? = null,
    val login: String? = null,
    val firstName: String? = null,
    val password: String? = null,
    val token:String?=null
    ):Parcelable