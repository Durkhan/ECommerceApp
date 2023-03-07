package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.product.ProductResponse
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke():ProductsResults<List<ProductResponse>>{
        return customerRepository.getAllProducts()
    }
}