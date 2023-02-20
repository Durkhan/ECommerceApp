package com.tasks.ecommerceapp.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.api.ApiAuthenticator
import com.tasks.ecommerceapp.api.CustomerRepository
import com.tasks.ecommerceapp.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInRepository: CustomerRepository,
    private val apiAuthenticator: ApiAuthenticator
) : ViewModel() {

    private val _signInResult = MutableLiveData<Results<CustomerLoginResponse>>()
    val signInResult: LiveData<Results<CustomerLoginResponse>> = _signInResult

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = signInRepository.signIn(email, password)
                setAuthToken(response.token)
                _signInResult.value = Results.Success(response)
            } catch (e: Exception) {
                _signInResult.value = Results.Error(e.message.toString())
            }
        }
    }
    private fun setAuthToken(token: String?) {
        apiAuthenticator.setAuthToken(token)
    }
}
