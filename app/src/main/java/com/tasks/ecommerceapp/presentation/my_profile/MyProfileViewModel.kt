package com.tasks.ecommerceapp.presentation.my_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.common.Results
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerResponse
import com.tasks.ecommerceapp.domain.usecases.ChangePasswordUseCase
import com.tasks.ecommerceapp.domain.usecases.GetCustomerUseCase
import com.tasks.ecommerceapp.domain.usecases.SendVerificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val sendVerificationUseCase: SendVerificationUseCase,
    private val getCustomerUseCase: GetCustomerUseCase,
    private val dataStoreManager: DataStoreManager
) :ViewModel(){

    var toEmail=""
    var login=""
    var verificationCode=""

    private val _customer = MutableLiveData<Results<CustomerResponse>>()
    val customer: LiveData<Results<CustomerResponse>> = _customer

    private val _changePasswordResult = MutableLiveData<Results<ChangePasswordResponse>>()
    val changePasswordResult: LiveData<Results<ChangePasswordResponse>> = _changePasswordResult

    fun changePassword(password: String, newPassword: String) {
        viewModelScope.launch {
            val result=changePasswordUseCase(dataStoreManager.token.first(),password,newPassword)
            _changePasswordResult.postValue(result)
        }
    }

    fun getCustomer() {
        viewModelScope.launch {
            val result=getCustomerUseCase(dataStoreManager.token.first())
            _customer.postValue(result)
        }
    }


    fun sendVerificationNumber(toEmail: String, code: Int): LiveData<Unit>{
        val result = MutableLiveData<Unit>()
        viewModelScope.launch {
            sendVerificationUseCase(toEmail, code)
        }
        return result
    }

    fun setDarkMode(boolean: Boolean){
        viewModelScope.launch {
            dataStoreManager.setDarkMode(boolean)
        }
    }

}