package com.tasks.ecommerceapp.utils

import android.content.Context
import android.widget.Button
import androidx.core.content.ContextCompat
import com.tasks.ecommerceapp.R

class CheckViewsValid(private var context: Context) {
    fun notEnabled(change: Button?) {
        change?.isEnabled=false
        change?.backgroundTintList= ContextCompat.getColorStateList(context,
            R.color.button_is_not_enabled
        )
        change?.setTextColor(
            ContextCompat.getColor(context,
                R.color.button_is_not_enabled_textcolor
            ))
    }
    fun setEnabled(change: Button?) {
        change?.isEnabled=true
        change?.backgroundTintList= ContextCompat.getColorStateList(context,
            R.color.button_tint_enabled
        )
        change?.setTextColor(
            ContextCompat.getColor(context,
                R.color.button_enabled_textcolor
            ))
    }
}