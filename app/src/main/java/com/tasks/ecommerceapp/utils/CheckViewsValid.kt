package com.tasks.ecommerceapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
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


    @SuppressLint("NewApi", "UseCompatTextViewDrawableApis")
    fun checkFocusedEdittext(editText:EditText){
        if (editText.text.isBlank()){
            editText.compoundDrawableTintList=ContextCompat.getColorStateList(context,
                R.color.edittextdrawable_notenabled
            )
        }
        else{
            editText.compoundDrawableTintList=ContextCompat.getColorStateList(context,
                R.color.editextdrawble_enabled
            )
        }

    }

}