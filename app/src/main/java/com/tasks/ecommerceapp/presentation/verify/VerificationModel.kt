package com.tasks.ecommerceapp.presentation.verify

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VerificationModel(
    var email:String,
    var verificationCode:String
):Parcelable