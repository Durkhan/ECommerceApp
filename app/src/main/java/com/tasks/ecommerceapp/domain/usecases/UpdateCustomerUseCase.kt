package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.data.repository.CustomerRepository
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.common.Results
import javax.inject.Inject

class UpdateCustomerUseCase @Inject constructor(private val customerRepository: CustomerRepository) {
    suspend fun updateCustomer(token: String, email: String, gender: String, firstName: String, lastName: String,
                               userName: String, avatarUrl: String, date: String,telephone:String): Results<CustomerRegisterResponse> {
        return customerRepository.updateCustomer(token, email, gender, firstName, lastName, userName, avatarUrl, date,telephone)
    }
}
