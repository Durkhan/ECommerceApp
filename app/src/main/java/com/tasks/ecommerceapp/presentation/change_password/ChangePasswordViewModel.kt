package com.tasks.ecommerceapp.presentation.change_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.common.Results
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.domain.usecases.change_password.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private var changePasswordUseCase: ChangePasswordUseCase,
    private var dataStoreManager: DataStoreManager
):ViewModel(){
    private val _changePasswordResult = MutableLiveData<Results<ChangePasswordResponse>>()
    val changePasswordResult: LiveData<Results<ChangePasswordResponse>> = _changePasswordResult

    fun changePassword(password: String, newPassword: String) {
        viewModelScope.launch {
            val result=changePasswordUseCase(dataStoreManager.token.first(),password,newPassword)
            _changePasswordResult.postValue(result)
        }
    }
}