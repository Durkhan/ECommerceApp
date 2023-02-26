package com.tasks.ecommerceapp.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.api.CustomerRepository
import com.tasks.ecommerceapp.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateCustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {


    val updateCustomerResult: LiveData<Results<CustomerRegisterResponse>> = customerRepository.updateResult


    val selectedImage: MutableLiveData<Uri?> by lazy {
        MutableLiveData<Uri?>()
    }
    fun updateCustomer(token:String,email: String, gender: String, firstName: String, lastName: String,userName:String,avatarUrl:String,date: String) {
        viewModelScope.launch {
            customerRepository.updateCustomer(token,email,gender,firstName,lastName,userName,avatarUrl,date)
        }
    }

}