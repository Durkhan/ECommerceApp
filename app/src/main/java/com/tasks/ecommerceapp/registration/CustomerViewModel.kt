package com.tasks.ecommerceapp.registration

import android.content.SyncRequest
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.api.CustomerRepository
import com.tasks.ecommerceapp.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    var firstName =""
    var lastName =""
    var userName =""
    var toEmail=""
    var verificationCode=""
    var password=""
    private val _changePasswordResult = MutableLiveData<Results<ChangePasswordResponse>>()
    val changePasswordResult: LiveData<Results<ChangePasswordResponse>> = _changePasswordResult

    private val _customer = MutableLiveData<Results<CustomerRegisterResponse>>()
    val customer: LiveData<Results<CustomerRegisterResponse>> = _customer


    val registerResult: LiveData<Results<CustomerRegisterResponse>> = repository.registerResult
    fun registerCustomer(email: String, password: String, firstName: String, lastName: String,userName:String) {
        viewModelScope.launch {
            repository.registerCustomer(email, password, firstName,lastName,userName)
        }
    }


    fun sendVerificationNumber(toEmail: String, code: Int): LiveData<Unit>{
        val result = MutableLiveData<Unit>()
        viewModelScope.launch {
            repository.sendVerificationNumber(toEmail, code)
        }
        return result
    }



    fun getCustomer(token:String,customer:String) {
        viewModelScope.launch {
            _customer.value = repository.getCustomer(token,customer)
        }
    }



    fun changePassword(authHeader:String,chunked:String,password: String, newPassword: String) {
        viewModelScope.launch {
            _changePasswordResult.value = repository.changePassword(authHeader,chunked,password,newPassword)
        }
    }
}