package com.tasks.ecommerceapp.data.api

import com.tasks.ecommerceapp.data.model.customer.cart.CartResponse
import com.tasks.ecommerceapp.data.model.customer.catalog.CatalogResponse
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordRequest
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginRequest
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.data.model.customer.orders.*
import com.tasks.ecommerceapp.data.model.customer.orders.CreateOrdersRequest
import com.tasks.ecommerceapp.data.model.customer.review.OrderReviewRequest
import com.tasks.ecommerceapp.data.model.customer.product.*
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterRequest
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerResponse
import com.tasks.ecommerceapp.data.model.customer.review.ProductReviewResponse
import retrofit2.http.*

interface CustomerService {
    @POST("customers/")
    suspend fun registerCustomer(@Body customer: CustomerRegisterRequest): CustomerRegisterResponse

    @POST("customers/login")
    suspend fun signIn(@Body request: CustomerLoginRequest): CustomerLoginResponse


    @GET("customers/customer")
    suspend fun getCustomer(@Header("Authorization") token:String): CustomerResponse


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

    @GET("comments/product/{productId}")
    suspend fun getProductReviews(@Path("productId") productId:String):List<ProductReviewResponse>

    @PUT("cart/{productId}")
    suspend fun addToCart(@Header("Authorization") token:String,@Path("productId") productId:String): CartResponse

    @GET("cart/")
    suspend fun getCartProducts(@Header("Authorization") token:String): CartResponse

    @DELETE("cart/{productId}")
    suspend fun deleteProductFromCart(@Header("Authorization") token:String,@Path("productId") productId:String): CartResponse

    @PUT ("wishlist/{productId}")
    suspend fun addWishlist(@Header("Authorization") token:String,@Path("productId") productId:String):ProductFilterResponse

    @GET("wishlist")
    suspend fun getWishListProducts(@Header("Authorization") token:String): ProductFilterResponse

    @DELETE("wishlist/{productId}")
    suspend fun deleteProductFromWishList(@Header("Authorization") token:String,@Path("productId") productId:String): ProductFilterResponse

    @POST ("orders/")
    suspend fun createOrder(@Body createOrdersRequest: CreateOrdersRequest): OrdersResponse

    @GET("orders")
    suspend fun getAllOrders(@Header("Authorization") token:String): List<Order>

    @GET("orders/{orderNo}")
    suspend fun getOrderByOrderNo(@Header("Authorization") token:String,@Path("orderNo") orderNo:String):Order

    @PUT("orders/{id}")
    suspend fun updateOrder(@Header("Authorization") token:String,@Path("id") orderId:String,@Body updateOrderRequest: UpdateOrderRequest):Order

    @POST("comments")
    suspend fun addReview(@Header("Authorization")token:String,@Body data: OrderReviewRequest?): ProductReviewResponse

}