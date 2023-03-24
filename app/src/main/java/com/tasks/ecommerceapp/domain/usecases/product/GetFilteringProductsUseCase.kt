package com.tasks.ecommerceapp.domain.usecases.product

import androidx.paging.PagingData
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilteringProductsUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
){
    suspend operator fun invoke(
        color: String?,
        size: String?,
        categories: String?,
        sort: String?
    ): Flow<PagingData<ProductsItem>> {
        return customerRepository.getFilteredProducts(color,size,categories,sort)
    }
}