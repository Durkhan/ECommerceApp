package com.tasks.ecommerceapp.common.listener

import com.tasks.ecommerceapp.data.model.customer.orders.Order


interface CompleteOrderListener {
    fun clickCheckOut(order: Order)
}