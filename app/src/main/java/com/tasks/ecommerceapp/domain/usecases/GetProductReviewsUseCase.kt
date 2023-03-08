package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.review.ProductReviewResponse
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import org.bson.types.ObjectId
import javax.inject.Inject

class GetProductReviewsUseCase @Inject constructor(
    private var customerRepository: CustomerRepository
) {
    suspend operator fun invoke(productId:String):ProductsResults<List<ProductReviewResponse>>{
        return customerRepository.getProductReviews(productId)
    }
}