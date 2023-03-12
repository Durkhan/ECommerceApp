package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.data.repository.CustomerRepository
import com.tasks.ecommerceapp.data.model.customer.register.CustomerResponse
import com.tasks.ecommerceapp.common.Results
import javax.inject.Inject

class GetCustomerUseCase @Inject constructor(
        private val customerRepository: CustomerRepository
) {
    suspend fun getCustomer(token:String): Results<CustomerResponse> {
        return customerRepository.getCustomer(token)
    }
}
