package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.data.model.customer.review.OrderReviewRequest
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class AddReviewUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(token:String,data: OrderReviewRequest) = customerRepository.addReview(token,data)
}