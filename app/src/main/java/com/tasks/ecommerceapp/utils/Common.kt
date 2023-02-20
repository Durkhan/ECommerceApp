package com.tasks.ecommerceapp.utils

import android.widget.Button
import androidx.core.content.ContextCompat
import com.tasks.ecommerceapp.R
import java.util.*


fun isEmail(text: String): Boolean {
        val regex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}")
        return regex.matches(text)
}
fun obfuscateString(text: String): String {
        if (text.length < 9) {
                return text
        }
        val start = 3
        val end = 9
        val substring = text.substring(start, end)
        val obfuscatedSubstring = substring.replace(Regex("[a-zA-Z0-9@.\\\\\\\\{2,}_%+-]"), "*")
        return text.substring(0, start) + obfuscatedSubstring + text.substring(end)
}
fun randomNumber():Int{
        val random = Random()
       return random.nextInt(9000) + 1000
}

