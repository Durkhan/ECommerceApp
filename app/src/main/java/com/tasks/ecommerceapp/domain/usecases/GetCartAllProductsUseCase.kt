package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.cart.CartResponse
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class GetCartAllProductsUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(token:String): ProductsResults<CartResponse> {
        return customerRepository.getCartProducts(token)
    }
}