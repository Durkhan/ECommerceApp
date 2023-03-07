package com.tasks.ecommerceapp.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.data.api.ApiAuthenticator
import com.tasks.ecommerceapp.domain.usecases.LoginCustomerUseCase
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.common.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginCustomerUseCase: LoginCustomerUseCase,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    private val _signInResult = MutableLiveData<Results<CustomerLoginResponse>>()
    val signInResult: LiveData<Results<CustomerLoginResponse>> = _signInResult

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val result = loginCustomerUseCase(email, password)
            _signInResult.postValue(result)
        }
    }

    fun saveToken(token:String){
        viewModelScope.launch {
            dataStoreManager.saveToken(token)
        }
    }
    fun saveRememberMe(boolean: Boolean){
        viewModelScope.launch {
            dataStoreManager.setRemember(boolean)
        }
    }


}
