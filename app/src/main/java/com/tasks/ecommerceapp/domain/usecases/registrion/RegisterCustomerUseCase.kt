package com.tasks.ecommerceapp.domain.usecases.registrion

import com.tasks.ecommerceapp.data.repository.CustomerRepository
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.common.Results
import javax.inject.Inject

class RegisterCustomerUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        userName: String
    ): Results<CustomerRegisterResponse> {
        return customerRepository.registerCustomer(email, password, firstName, lastName, userName)
    }
}
