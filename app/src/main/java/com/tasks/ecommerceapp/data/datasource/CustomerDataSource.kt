package com.tasks.ecommerceapp.data.datasource

import androidx.paging.PagingData
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.catalog.CatalogResponse
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordRequest
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginRequest
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.data.model.customer.product.*
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterRequest
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerResponse
import kotlinx.coroutines.flow.Flow

interface CustomerDataSource {
    suspend fun registerCustomer(customer: CustomerRegisterRequest): CustomerRegisterResponse
    suspend fun loginCustomer(customer: CustomerLoginRequest):CustomerLoginResponse
    suspend fun getCustomer(token:String,customer:String): CustomerResponse
    suspend fun changePassword(token:String,passwords:ChangePasswordRequest):ChangePasswordResponse
    suspend fun updateCustomer(token: String,customer: CustomerRegisterRequest):CustomerRegisterResponse
    suspend fun getCatalog():List<CatalogResponse>
    suspend fun getAllProducts():List<ProductResponse>
    suspend fun getFilteredProducts(color: String?, size: String?, categories: String?, sort: String?):Flow<PagingData<ProductsItem>>
    suspend fun getSearchedProducts(searchProductRequest: SearchProductRequest):List<SearchProductResponse>
}