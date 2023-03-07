package com.tasks.ecommerceapp.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.domain.usecases.ChangePasswordUseCase
import com.tasks.ecommerceapp.domain.usecases.GetCustomerUseCase
import com.tasks.ecommerceapp.domain.usecases.RegisterCustomerUseCase
import com.tasks.ecommerceapp.domain.usecases.SendVerificationUseCase
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerResponse
import com.tasks.ecommerceapp.common.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityCustomerViewModel @Inject constructor(
    private val getCustomerUseCase: GetCustomerUseCase,
    private val registerCustomerUseCase: RegisterCustomerUseCase,
    private val sendVerificationUseCase: SendVerificationUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    var firstName =""
    var lastName =""
    var userName =""
    var toEmail=""
    var verificationCode=""


    private val _changePasswordResult = MutableLiveData<Results<ChangePasswordResponse>>()
    val changePasswordResult: LiveData<Results<ChangePasswordResponse>> = _changePasswordResult

    private val _customer = MutableLiveData<Results<CustomerResponse>>()
    val customer: LiveData<Results<CustomerResponse>> = _customer


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
            sendVerificationUseCase.invoke(toEmail, code)
        }
        return result
    }



    fun getCustomer(token:String,customer:String) {
        viewModelScope.launch {
            _customer.postValue(getCustomerUseCase.getCustomer(token,customer))
        }
    }



    fun changePassword(authHeader:String,password: String, newPassword: String) {
        viewModelScope.launch {
            _changePasswordResult.postValue(changePasswordUseCase.changePassword(authHeader,password,newPassword))
        }
    }

    fun saveNotFistTime(){
        viewModelScope.launch {
            dataStoreManager.setFirstTime(false)
        }
    }
    suspend fun getToken():String{
            return dataStoreManager.token.first()
    }


    suspend fun getRememberUser():Boolean{
        return dataStoreManager.isRemember.first()
    }

    suspend fun getFirstTime():Boolean{
        return dataStoreManager.isFirstTime.first()
    }

    suspend fun getPin():String{
        return dataStoreManager.pin.first()
    }


}