package com.tasks.ecommerceapp.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView?.getCurrentPosition(): Int {
    return (this?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
}