package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.data.repository.CustomerRepository
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.common.Results
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(private val repository: CustomerRepository) {

    suspend operator fun invoke(authHeader: String, password: String, newPassword: String): Results<ChangePasswordResponse> {
        return repository.changePassword(authHeader, password, newPassword)
    }
}
