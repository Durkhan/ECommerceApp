package com.tasks.ecommerceapp.data.api

import com.tasks.ecommerceapp.data.model.customer.product.SearchProductRequest
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordRequest
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginRequest
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.data.model.customer.catalog.CatalogResponse
import com.tasks.ecommerceapp.data.model.customer.product.ProductFilterResponse
import com.tasks.ecommerceapp.data.model.customer.product.ProductResponse
import com.tasks.ecommerceapp.data.model.customer.product.SearchProductResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterRequest
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerResponse
import okhttp3.Response
import retrofit2.http.*

interface CustomerService {
    @POST("customers/")
    suspend fun registerCustomer(@Body customer: CustomerRegisterRequest): CustomerRegisterResponse

    @POST("customers/login")
    suspend fun signIn(@Body request: CustomerLoginRequest): CustomerLoginResponse


    @GET("customers/customer")
    suspend fun getCustomer(@Header("Authorization") token:String,@Query("customer") customer: String,): CustomerResponse


    @PUT("customers/password")
    suspend fun changePassword(@Header("Authorization") token:String,
                               @Body request: ChangePasswordRequest): ChangePasswordResponse

    @PUT("customers/")
    suspend fun updateCustomer(@Header("Authorization") token:String,@Body customer: CustomerRegisterRequest): CustomerRegisterResponse


    @GET("catalog")
    suspend fun getCatalog(): List<CatalogResponse>

    @GET("products")
    suspend fun getALlProducts(): List<ProductResponse>

    @GET("products/filter")
    suspend fun getFilteredProducts(
        @Query("color") color: String?,
        @Query("size") size: String?,
        @Query("categories") categories: String?,
        @Query("perPage") perPage: Int,
        @Query("startPage") startPage: Int,
        @Query("sort") sort: String?
    ): ProductFilterResponse


    @POST("products/search")
    suspend fun getSearchedProducts(@Body searched: SearchProductRequest?):List<SearchProductResponse>
}