package com.tasks.ecommerceapp.data.repository

import androidx.paging.PagingData
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.common.Results
import com.tasks.ecommerceapp.data.api.ApiAuthenticator
import com.tasks.ecommerceapp.data.datasource.CustomerDataSource
import com.tasks.ecommerceapp.data.model.customer.cart.CartProductsItem
import com.tasks.ecommerceapp.data.model.customer.catalog.CatalogResponse
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordRequest
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginRequest
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.data.model.customer.product.*
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterRequest
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerResponse
import com.tasks.ecommerceapp.data.model.customer.cart.CartResponse
import com.tasks.ecommerceapp.data.model.customer.orders.*
import com.tasks.ecommerceapp.data.model.customer.review.ProductReviewResponse
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

    suspend fun getCustomer(token:String): Results<CustomerResponse> {
        return try {
            val response = customerDataSource.getCustomer(token)
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

    suspend fun getAllOrders(token:String) : ProductsResults<List<Order>>{
        return try {
            ProductsResults.Loading<List<Order>>(true)
            val response = customerDataSource.getAllOrders(token)
            ProductsResults.Success(response)
        }catch (e:Exception){
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getProductReview(productId:String):ProductsResults<List<ProductReviewResponse>>{
        return try {
            ProductsResults.Loading<List<ProductReviewResponse>>(true)
            val response = customerDataSource.getProductReview(productId)
            ProductsResults.Success(response)
        }catch (e:Exception){
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }
    }


    suspend fun addToCart(token: String,productId:String): ProductsResults<CartResponse>{
        return try {
            ProductsResults.Loading<CartResponse>(true)
            val response = customerDataSource.addToCart(token,productId)
            ProductsResults.Success(response)
        }catch (e:Exception){
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getCartProducts(token: String): ProductsResults<CartResponse> {
        return try {
            ProductsResults.Loading<CartResponse>(true)
            val response = customerDataSource.getCartProducts(token)
            ProductsResults.Success(response)
        } catch (e: Exception) {
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun deleteCartFromProduct(token: String,productId:String): ProductsResults<CartResponse>{
        return try {
            ProductsResults.Loading<CartResponse>(true)
            val response = customerDataSource.deleteProductFromCart(token,productId)
            ProductsResults.Success(response)
        }catch (e:Exception){
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun createOrder(customerId:String,email: String,mobile:String,productsItem:List<CartProductsItem>): ProductsResults<OrdersResponse>{
        return try {
            ProductsResults.Loading<CreateOrdersRequest>(true)
            val response = customerDataSource.createOrder(CreateOrdersRequest(
                customerId=customerId,
                email =email,
                mobile = mobile,
                products=productsItem)
            )
            ProductsResults.Success(response)
        }catch (e:Exception){
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun addToWishList(token: String,productId:String): ProductsResults<ProductFilterResponse>{
        return try {
            ProductsResults.Loading<ProductFilterResponse>(true)
            val response = customerDataSource.addToWishlist(token,productId)
            ProductsResults.Success(response)
        }catch (e:Exception){
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getWishListProducts(token: String): ProductsResults<ProductFilterResponse> {
        return try {
            ProductsResults.Loading<ProductFilterResponse>(true)
            val response = customerDataSource.getWishListProducts(token)
            ProductsResults.Success(response)
        } catch (e: Exception) {
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }
    }
    suspend fun deleteCartFromWishList(token: String,productId:String): ProductsResults<ProductFilterResponse>{
        return try {
            ProductsResults.Loading<ProductFilterResponse>(true)
            val response = customerDataSource.deleteProductFromWishList(token,productId)
            ProductsResults.Success(response)
        }catch (e:Exception){
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getOrderByOrderNo(token: String,orderNo:String):ProductsResults<Order>{
        return try {
            ProductsResults.Loading<Order>(true)
            val response = customerDataSource.getOrderByOrderNo(token,orderNo)
            ProductsResults.Success(response)
        }catch (e:Exception){
            ProductsResults.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun updateOrder(token:String,orderId: String,email: String,shipping:String):ProductsResults<Order>{
         return try{
             ProductsResults.Loading<Order>(true)
             val response = customerDataSource.updateOrder(token,orderId,UpdateOrderRequest(email,shipping))
             ProductsResults.Success(response)
         }catch (e:Exception){
             ProductsResults.Error(e.message ?: "Unknown error occurred")
         }
    }
}