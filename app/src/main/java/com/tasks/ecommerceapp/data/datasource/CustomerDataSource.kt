package com.tasks.ecommerceapp.data.datasource

import androidx.paging.PagingData
import com.tasks.ecommerceapp.data.model.customer.cart.CartResponse
import com.tasks.ecommerceapp.data.model.customer.catalog.CatalogResponse
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordRequest
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginRequest
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.data.model.customer.orders.CreateOrdersRequest
import com.tasks.ecommerceapp.data.model.customer.review.OrderReviewRequest
import com.tasks.ecommerceapp.data.model.customer.orders.*
import com.tasks.ecommerceapp.data.model.customer.product.*
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterRequest
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerResponse
import com.tasks.ecommerceapp.data.model.customer.review.ProductReviewResponse
import kotlinx.coroutines.flow.Flow

interface CustomerDataSource {
    suspend fun registerCustomer(customer: CustomerRegisterRequest): CustomerRegisterResponse
    suspend fun loginCustomer(customer: CustomerLoginRequest):CustomerLoginResponse
    suspend fun getCustomer(token:String): CustomerResponse
    suspend fun changePassword(token:String,passwords:ChangePasswordRequest):ChangePasswordResponse
    suspend fun updateCustomer(token: String,customer: CustomerRegisterRequest):CustomerRegisterResponse
    suspend fun getCatalog():List<CatalogResponse>
    suspend fun getAllProducts():List<ProductResponse>
    suspend fun getFilteredProducts(color: String?, size: String?, categories: String?, sort: String?):Flow<PagingData<ProductsItem>>
    suspend fun getSearchedProducts(searchProductRequest: SearchProductRequest):List<SearchProductResponse>
    suspend fun getProductReview(productId:String):List<ProductReviewResponse>
    suspend fun addToCart(token: String,productId:String): CartResponse
    suspend fun getCartProducts(token: String):CartResponse
    suspend fun deleteProductFromCart(token: String,productId: String):CartResponse
    suspend fun getAllOrders(token:String): List<Order>
    suspend fun createOrder(createOrdersRequest: CreateOrdersRequest):OrdersResponse
    suspend fun addToWishlist(token:String,productId:String):ProductFilterResponse
    suspend fun getWishListProducts(token:String): ProductFilterResponse
    suspend fun deleteProductFromWishList(token:String,productId:String): ProductFilterResponse
    suspend fun getOrderByOrderNo(token:String,orderNo:String):Order
    suspend fun updateOrder(token:String,orderId:String,updateOrderRequest: UpdateOrderRequest):Order
    suspend fun addReview(token:String,data: OrderReviewRequest): ProductReviewResponse
}