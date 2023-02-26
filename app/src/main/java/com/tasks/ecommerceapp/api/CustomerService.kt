package com.tasks.ecommerceapp.api

import com.tasks.ecommerceapp.customer.chagepassword.ChangePasswordRequest
import com.tasks.ecommerceapp.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.customer.login.CustomerLogin
import com.tasks.ecommerceapp.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.customer.register.CustomerRegister
import com.tasks.ecommerceapp.customer.register.CustomerRegisterResponse
import retrofit2.http.*

interface CustomerService {
    @POST("customers/")
    suspend fun registerCustomer(@Body customer: CustomerRegister): CustomerRegisterResponse

    @POST("customers/login")
    suspend fun signIn(@Body request: CustomerLogin): CustomerLoginResponse


    @GET("customers/customer")
    suspend fun getCustomer(@Header("Authorization") token:String,@Query("customer") customer: String,): CustomerRegisterResponse


    @PUT("customers/password")
    suspend fun changePassword(@Header("Authorization") token:String,
                               @Body request: ChangePasswordRequest): ChangePasswordResponse

    @PUT("customers/")
    suspend fun updateCustomer(@Header("Authorization") token:String,@Body customer: CustomerRegister): CustomerRegisterResponse

}