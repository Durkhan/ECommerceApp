package com.tasks.ecommerceapp.domain.usecases


import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.cart.CartResponse
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private var customerRepository: CustomerRepository
) {
    suspend operator fun invoke(token: String,productId:String):ProductsResults<CartResponse>{
        return customerRepository.addToCart(token,productId)
    }
}