package com.tasks.ecommerceapp.presentation.registration


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.domain.usecases.RegisterCustomerUseCase
import com.tasks.ecommerceapp.domain.usecases.SendVerificationUseCase
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.common.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityCustomerViewModel @Inject constructor(
    private val registerCustomerUseCase: RegisterCustomerUseCase,
    private val sendVerificationUseCase: SendVerificationUseCase,
) : ViewModel() {
    var firstName =""
    var lastName =""
    var userName =""
    var toEmail=""
    var verificationCode=""

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


    fun sendVerificationNumber(toEmail: String, code: Int): LiveData<Unit>{
        val result = MutableLiveData<Unit>()
        viewModelScope.launch {
            sendVerificationUseCase(toEmail, code)
        }
        return result
    }



}