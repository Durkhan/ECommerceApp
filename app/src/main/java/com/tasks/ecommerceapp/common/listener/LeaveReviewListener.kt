package com.tasks.ecommerceapp.common.listener

import com.tasks.ecommerceapp.data.model.customer.orders.Order

interface LeaveReviewListener {
    fun leaveReview(order: Order)
}