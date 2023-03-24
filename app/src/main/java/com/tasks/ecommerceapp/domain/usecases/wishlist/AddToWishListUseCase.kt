package com.tasks.ecommerceapp.domain.usecases.wishlist

import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.product.ProductFilterResponse
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class AddToWishListUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(token: String,productId: String):ProductsResults<ProductFilterResponse>{
        return customerRepository.addToWishList(token,productId)
    }
}