package com.tasks.ecommerceapp.presentation.set_pin
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetPinViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
):ViewModel() {


    fun savePin(pin: String) {
        viewModelScope.launch {
            dataStoreManager.savePin(pin)
        }
    }
    fun checkPin(pin: String): Boolean {
        return true
    }


}