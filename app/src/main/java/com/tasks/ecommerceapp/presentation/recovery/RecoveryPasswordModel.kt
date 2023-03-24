package com.tasks.ecommerceapp.presentation.recovery

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecoveryPasswordModel(
    var email:String,
    var login:String,
    var code:String? = null
):Parcelable