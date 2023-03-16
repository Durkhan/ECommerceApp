package com.tasks.ecommerceapp.presentation.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.domain.usecases.RegisterCustomerUseCase
import com.tasks.ecommerceapp.domain.usecases.SendVerificationUseCase
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.common.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ActivityCustomerViewModel @Inject constructor(
    private val registerCustomerUseCase: RegisterCustomerUseCase,
    private val sendVerificationUseCase: SendVerificationUseCase,
    private val dataStoreManager: DataStoreManager
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


    fun saveNotFistTime(){
        viewModelScope.launch {
            dataStoreManager.setFirstTime(false)
        }
    }

    suspend fun isAccessTokenExpired(): Boolean {
        return try{
            val token=getToken().removePrefix("Bearer ")
            val jwt: DecodedJWT = JWT.decode(token)
            val expirationTime: Date = jwt.expiresAt

            Date().after(expirationTime)
        }catch (e:Exception){
            Log.d("AccessTokenDecoded",e.message.toString())
            true
        }

    }

    suspend fun isDarkMode():Boolean{
        return dataStoreManager.isDarkMode.first()
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