package com.tasks.ecommerceapp.data.repository


import androidx.paging.PagingData
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.api.ApiAuthenticator
import com.tasks.ecommerceapp.data.datasource.CustomerDataSource
import com.tasks.ecommerceapp.data.model.customer.product.SearchProductRequest
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordRequest
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginRequest
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.data.model.customer.catalog.CatalogResponse
import com.tasks.ecommerceapp.data.model.customer.product.ProductResponse
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.data.model.customer.product.SearchProductResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterRequest
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerResponse
import com.tasks.ecommerceapp.common.Results
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val customerDataSource: CustomerDataSource,
    private val apiAuthenticator: ApiAuthenticator,
) {


    suspend fun registerCustomer(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        userName: String
    ): Results<CustomerRegisterResponse> {
        return try {
            val request = CustomerRegisterRequest(
                firstName = firstName,
                lastName = lastName,
                login = userName,
                email = email,
                password = password,
                isAdmin = false
            )
            val response = customerDataSource.registerCustomer(request)
            Results.Success(response)
        } catch (e:IOException) {
            Results.Error(e.message ?: "Unknown error")
       } catch (e: HttpException) {
        Results.Error(e.message ?: "Unknown error")
    }
    }


    suspend fun signIn(email: String, password: String): Results<CustomerLoginResponse> {
        return try {
            val request = CustomerLoginRequest(email, password)
            val response=customerDataSource.loginCustomer(request)
            setAuthToken(response.token)
            Results.Success(response)
        }catch (e:Exception){
            Results.Error(e.message ?: "Unknown error")
        }
    }



    private fun setAuthToken(token: String?) {
        apiAuthenticator.setAuthToken(token)
    }



    suspend fun getCustomer(token:String,customer:String): Results<CustomerResponse> {
        return try {
            val response = customerDataSource.getCustomer(token,customer)
            Results.Success(response)
        } catch (e: Exception) {
            Results.Error(e.message ?: "An error occurred")
        }
    }



    suspend fun changePassword(token: String,password: String, newPassword:String): Results<ChangePasswordResponse> {
        val request = ChangePasswordRequest(password=password,newPassword=newPassword)
        return try {
            val response = customerDataSource.changePassword(token,request)
            Results.Success(response)
        } catch (e: Exception) {
            Results.Error(e.message.toString())
        } catch (e: IOException) {
            Results.Error(e.message.toString())
        }
    }

     suspend fun updateCustomer(token: String,email: String, gender: String,
                                firstName: String, lastName: String,userName:String,
                                avatarUrl:String,date:String,telephone:String) :Results<CustomerRegisterResponse>
     {
        return try {
            val request =CustomerRegisterRequest(
                    firstName=firstName,
                    lastName = lastName,
                    login = userName,
                    gender = gender,
                    email = email,
                    avatarUrl = avatarUrl,
                    date = date,
                    telephone = telephone
                )

           val response=customerDataSource.updateCustomer(token,request)
            Results.Success(response)
        } catch (e: IOException) {
            Results.Error(e.message ?: "Unknown error")
        } catch (e: HttpException) {
            Results.Error(e.message ?: "Unknown error")
        }
    }


    suspend fun getCatalog(): ProductsResults<List<CatalogResponse>> {
        return try {
            ProductsResults.Loading<List<CatalogResponse>>(true)
            val response = customerDataSource.getCatalog()
            ProductsResults.Success(response)
        } catch (e: Exception) {
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }
    }


    suspend fun getAllProducts(): ProductsResults<List<ProductResponse>> {
        return try {
            ProductsResults.Loading<List<ProductResponse>>(true)
            val response = customerDataSource.getAllProducts()
            ProductsResults.Success(response)
        } catch (e: Exception) {
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }

    }


    suspend fun getFilteredProducts(
        color: String?,
        size: String?,
        categories: String?,
        sort: String?
    ):Flow<PagingData<ProductsItem>> {
         return customerDataSource.getFilteredProducts(color,size,categories,sort)
    }


    suspend fun getSearchedProducts(searchedText:String):ProductsResults<List<SearchProductResponse>>{
        val request = SearchProductRequest(searchedText)
        return try {
            ProductsResults.Loading<List<SearchProductResponse>>(true)
            val response = customerDataSource.getSearchedProducts(request)
            ProductsResults.Success(response)
        } catch (e: Exception) {
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }
    }

}