package com.tasks.ecommerceapp.extensions

import android.view.View
import androidx.core.view.isVisible

fun View.hide() {
    this.isVisible = false
}

fun View.show() {
    this.isVisible = true
}