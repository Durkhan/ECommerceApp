package com.tasks.ecommerceapp.presentation.registration


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.domain.usecases.registrion.RegisterCustomerUseCase
import com.tasks.ecommerceapp.domain.usecases.registrion.SendVerificationUseCase
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.common.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerCustomerUseCase: RegisterCustomerUseCase,
    private val sendVerificationUseCase: SendVerificationUseCase,
) : ViewModel() {

    private val _registerResult = MutableLiveData<Results<CustomerRegisterResponse>>()
    val registerResult: LiveData<Results<CustomerRegisterResponse>> = _registerResult

    fun registerCustomer(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        userName: String
    ) {
        viewModelScope.launch {
            val result = registerCustomerUseCase(email, password, firstName, lastName, userName)
            _registerResult.postValue(result)
        }
    }


    fun sendVerificationNumber(toEmail: String, code: Int){
        viewModelScope.launch {
            sendVerificationUseCase(toEmail, code)
        }
    }



}