package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.data.model.customer.orders.OrderReviewRequest
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class AddReviewUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend fun addReview(data: OrderReviewRequest) = customerRepository.addReview(data)
}