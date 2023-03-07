package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.product.SearchProductResponse
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class GetSearchedProductsUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(searchedText:String): ProductsResults<List<SearchProductResponse>> {
        return customerRepository.getSearchedProducts(searchedText)
    }
}
