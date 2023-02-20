package com.tasks.ecommerceapp.customer.register

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerRegister(
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
    ):Parcelable