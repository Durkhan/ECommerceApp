package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.data.repository.CustomerRepository
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.common.Results
import javax.inject.Inject

class LoginCustomerUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Results<CustomerLoginResponse> {
        return customerRepository.signIn(email, password)
    }
}
