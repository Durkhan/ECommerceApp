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
import com.tasks.ecommerceapp.common.listener.UploadImageCallback
import com.tasks.ecommerceapp.domain.usecases.UploadImageCloudinaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateCustomerViewModel @Inject constructor(
    private val updateCustomerUseCase: UpdateCustomerUseCase,
    private val uploadImageCloudinaryUseCase: UploadImageCloudinaryUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var avatarUrl: String?=null

    private val _updateCustomerResult=MutableLiveData<Results<CustomerRegisterResponse>>()
    val updateCustomerResult:LiveData<Results<CustomerRegisterResponse>> = _updateCustomerResult

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = _imageUrl


    suspend fun getToken():String {
            return dataStoreManager.token.first().toString()
    }


    fun updateCustomer(token:String,email: String, gender: String, firstName: String, lastName: String,userName:String,avatarUrl:String,date: String,telephone:String) {
        viewModelScope.launch {
            val updateCustomer=updateCustomerUseCase(token,email,gender,firstName,lastName,userName,avatarUrl,date, telephone)
            _updateCustomerResult.postValue(updateCustomer)
        }
    }


    fun getUploadedImageUrl(uri: Uri, context: Context) {
        uploadImageCloudinaryUseCase.invoke(uri, context, object : UploadImageCallback {
            override fun onUploadSuccess(url: String) {
               _imageUrl.postValue(url)
            }
        })
    }

}