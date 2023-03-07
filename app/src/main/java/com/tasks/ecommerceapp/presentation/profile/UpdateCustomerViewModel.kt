package com.tasks.ecommerceapp.presentation.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.domain.usecases.UpdateCustomerUseCase
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.common.Results
import com.tasks.ecommerceapp.domain.usecases.UploadImageCloudinaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateCustomerViewModel @Inject constructor(
    private val updateCustomerUseCase: UpdateCustomerUseCase,
    private val uploadImageCloudinaryUseCase: UploadImageCloudinaryUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _updateCustomerResult=MutableLiveData<Results<CustomerRegisterResponse>>()
    val updateCustomerResult:LiveData<Results<CustomerRegisterResponse>> = _updateCustomerResult


    val selectedImage: MutableLiveData<Uri> by lazy {
        MutableLiveData<Uri>()
    }

    suspend fun getToken():String {
            return dataStoreManager.token.first().toString()
    }
    fun updateCustomer(token:String,email: String, gender: String, firstName: String, lastName: String,userName:String,avatarUrl:String,date: String,telephone:String) {
        viewModelScope.launch {
            val updateCustomer=updateCustomerUseCase.updateCustomer(token,email,gender,firstName,lastName,userName,avatarUrl,date, telephone)
            _updateCustomerResult.postValue(updateCustomer)
        }
    }
    suspend fun getUploadedImageUrl(uri:Uri, context: Context):String{
        return uploadImageCloudinaryUseCase.invoke(uri,context)
    }
}