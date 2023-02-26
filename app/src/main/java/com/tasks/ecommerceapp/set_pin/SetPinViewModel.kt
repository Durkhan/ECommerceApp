package com.tasks.ecommerceapp.set_pin
import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.utils.DataStoreManager
import com.tasks.ecommerceapp.utils.datastore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetPinViewModel @Inject constructor():ViewModel() {
    private val _isFingerprintEnabled = MutableLiveData<Boolean>()
    val isFingerprintEnabled: LiveData<Boolean> = _isFingerprintEnabled
    private lateinit var dataStoreManager: DataStoreManager
    fun savePin(pin: String,context: Context) {
        viewModelScope.launch {
            dataStoreManager=DataStoreManager(context.datastore)
            dataStoreManager.savePin(pin)
        }
    }
    fun checkPin(pin: String): Boolean {
        return true
    }


    fun enableFingerprint(fragment: Fragment) {
        val executor = ContextCompat.getMainExecutor(fragment.requireContext())
        val biometricPrompt = BiometricPrompt(fragment,executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                _isFingerprintEnabled.value = false
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                _isFingerprintEnabled.value = true
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint Authentication")
            .setSubtitle("Scan your fingerprint to enable access")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}