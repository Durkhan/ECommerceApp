package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.product.ProductFilterResponse
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class GetWishListProductsUseCase @Inject constructor(
    private val  customerRepository: CustomerRepository
) {
    suspend operator fun invoke(token:String):ProductsResults<ProductFilterResponse>{
        return customerRepository.getWishListProducts(token)
    }
}