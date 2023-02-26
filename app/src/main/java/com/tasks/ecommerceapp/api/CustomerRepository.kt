package com.tasks.ecommerceapp.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tasks.ecommerceapp.customer.chagepassword.ChangePasswordRequest
import com.tasks.ecommerceapp.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.customer.login.CustomerLogin
import com.tasks.ecommerceapp.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.customer.register.CustomerRegister
import com.tasks.ecommerceapp.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.utils.Constants
import com.tasks.ecommerceapp.utils.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class CustomerRepository @Inject constructor(private val service: CustomerService) {
    private val _registerResult = MutableLiveData<Results<CustomerRegisterResponse>>()
    val registerResult: LiveData<Results<CustomerRegisterResponse>> = _registerResult

    private val _updateResult = MutableLiveData<Results<CustomerRegisterResponse>>()
    val updateResult: LiveData<Results<CustomerRegisterResponse>> = _updateResult

    suspend fun registerCustomer(email: String, password: String, firstName: String, lastName: String,userName:String) {
        try {
            val response = service.registerCustomer(
                CustomerRegister(
                firstName= firstName,
                lastName= lastName,
                login= userName,
                email= email,
                password= password,
                isAdmin=false
            )
            )
            _registerResult.postValue(Results.Success(response))
        } catch (e: IOException) {
            _registerResult.postValue(Results.Error(e.message ?: "Unknown error"))
        } catch (e: HttpException) {
            _registerResult.postValue(Results.Error(e.message ?: "Unknown error"))
        }
    }


    suspend fun signIn(email: String, password: String): CustomerLoginResponse {
        val request = CustomerLogin(email, password)
        return service.signIn(request)
    }

    suspend fun getCustomer(token:String,customer:String): Results<CustomerRegisterResponse> {
        return try {
            val response = service.getCustomer(token,customer)
            Results.Success(response)
        } catch (e: Exception) {
            Results.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun sendVerificationNumber(toEmail: String, code: Int){
        return withContext(Dispatchers.IO) {
            try {
                val host = "smtp.gmail.com"
                val port = "587"

                val props = Properties()
                props["mail.smtp.auth"] = "true"
                props["mail.smtp.starttls.enable"] = "true"
                props["mail.smtp.host"] = host
                props["mail.smtp.port"] = port

                val session = Session.getDefaultInstance(props, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(Constants.APP_EMAIL, Constants.APP_PASSWORD)
                    }
                })

                val message = MimeMessage(session)
                message.setFrom(InternetAddress(Constants.APP_EMAIL))
                message.setRecipient(Message.RecipientType.TO, InternetAddress(toEmail))
                message.subject = "Verification Code"
                message.setText("Your verification code is $code")

                Transport.send(message)

            } catch (e: Exception) {
                Log.d("TAG",e.message.toString())
            }
        }
    }


    suspend fun changePassword(authHeader: String,chunked:String,password: String, newPassword:String): Results<ChangePasswordResponse> {
        val request = ChangePasswordRequest(password=password,newPassword=newPassword)
        return try {
            val response = service.changePassword(authHeader,request)
            Results.Success(response)
        } catch (e: Exception) {
            Results.Error(e.message.toString())
        }
    }

     suspend fun updateCustomer(token: String,email: String, gender: String, firstName: String, lastName: String,userName:String,avatarUrl:String,date:String) {
        return try {
            val response = service.updateCustomer(token = token,
                CustomerRegister(
                    firstName=firstName,
                    lastName = lastName,
                    login = userName,
                    gender = gender,
                    email = email,
                    avatarUrl = avatarUrl,
                    date = date
                )
            )
           _updateResult.postValue(Results.Success(response))

        } catch (e: IOException) {
            _updateResult.postValue(Results.Error(e.message ?: "Unknown error"))
        } catch (e: HttpException) {
            _updateResult.postValue(Results.Error(e.message ?: "Unknown error"))
        }
    }

}