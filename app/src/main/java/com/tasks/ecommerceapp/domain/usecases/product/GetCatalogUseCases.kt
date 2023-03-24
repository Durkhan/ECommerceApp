package com.tasks.ecommerceapp.domain.usecases.product

import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.catalog.CatalogResponse
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class GetCatalogUseCases @Inject constructor(
    private val customerRepository: CustomerRepository
) {
        suspend operator fun invoke():ProductsResults<List<CatalogResponse>>{
            return customerRepository.getCatalog()
        }
}