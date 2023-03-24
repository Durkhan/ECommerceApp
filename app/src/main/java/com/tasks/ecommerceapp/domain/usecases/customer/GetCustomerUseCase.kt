package com.tasks.ecommerceapp.domain.usecases.customer

import com.tasks.ecommerceapp.data.repository.CustomerRepository
import com.tasks.ecommerceapp.data.model.customer.register.CustomerResponse
import com.tasks.ecommerceapp.common.Results
import javax.inject.Inject

class GetCustomerUseCase @Inject constructor(
        private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(token:String): Results<CustomerResponse> {
        return customerRepository.getCustomer(token)
    }
}
