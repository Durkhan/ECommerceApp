package com.tasks.ecommerceapp.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tasks.ecommerceapp.data.api.CustomerService
import com.tasks.ecommerceapp.data.model.customer.cart.CartProductsItem
import com.tasks.ecommerceapp.data.model.customer.cart.CartResponse
import com.tasks.ecommerceapp.data.model.customer.catalog.CatalogResponse
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordRequest
import com.tasks.ecommerceapp.data.model.customer.chagepassword.ChangePasswordResponse
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginRequest
import com.tasks.ecommerceapp.data.model.customer.login.CustomerLoginResponse
import com.tasks.ecommerceapp.data.model.customer.orders.*
import com.tasks.ecommerceapp.data.model.customer.orders.CreateOrdersRequest
import com.tasks.ecommerceapp.data.model.customer.orders.GetAllOrdersResponse
import com.tasks.ecommerceapp.data.model.customer.orders.OrderReviewRequest
import com.tasks.ecommerceapp.data.model.customer.product.*
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterRequest
import com.tasks.ecommerceapp.data.model.customer.register.CustomerRegisterResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerResponse
import com.tasks.ecommerceapp.data.model.customer.review.ProductReviewResponse
import com.tasks.ecommerceapp.presentation.allproducts.ProductFilterPagingSource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Header
import javax.inject.Inject

class CustomerDataSourceImpl @Inject constructor(
    private val customerService: CustomerService
) : CustomerDataSource {
    override suspend fun registerCustomer(customer: CustomerRegisterRequest): CustomerRegisterResponse {
        return customerService.registerCustomer(
            CustomerRegisterRequest(
                firstName = customer.firstName,
                lastName = customer.lastName,
                login = customer.login,
                email = customer.email,
                password = customer.password,
                isAdmin = customer.isAdmin
            )
        )
    }

    override suspend fun loginCustomer(customer: CustomerLoginRequest): CustomerLoginResponse {
        return customerService.signIn(CustomerLoginRequest(
            loginOrEmail = customer.loginOrEmail,
            password = customer.password
        )
        )
    }

    override suspend fun getCustomer(token: String): CustomerResponse {
        return customerService.getCustomer(
            token=token
        )
    }



    override suspend fun changePassword(token: String, passwords: ChangePasswordRequest): ChangePasswordResponse {
        return customerService.changePassword(
            token=token,
            ChangePasswordRequest(
                password=passwords.password,
                newPassword = passwords.newPassword
            )
        )
    }

    override suspend fun updateCustomer(
        token: String,
        customer: CustomerRegisterRequest
    ): CustomerRegisterResponse {
        return customerService.updateCustomer(
            token=token,
            CustomerRegisterRequest(
                email = customer.email,
                gender = customer.gender,
                firstName = customer.firstName,
                lastName = customer.lastName,
                login = customer.login,
                avatarUrl = customer.avatarUrl,
                date = customer.date,
                telephone = customer.telephone

            )
        )
    }

    override suspend fun getCatalog(): List<CatalogResponse> {
       return customerService.getCatalog()
    }

    override suspend fun getAllProducts(): List<ProductResponse> {
        return customerService.getALlProducts()
    }

    override suspend fun getFilteredProducts(
        color: String?,
        size: String?,
        categories: String?,
        sort: String?
    ): Flow<PagingData<ProductsItem>>{
        return Pager(
                config = PagingConfig(pageSize = 3, enablePlaceholders = true),
                pagingSourceFactory = {
                    ProductFilterPagingSource(customerService, color, size, categories, sort)
                }
        ).flow
}

    override suspend fun getSearchedProducts(searchProductRequest: SearchProductRequest):List<SearchProductResponse> {
        return customerService.getSearchedProducts(
            SearchProductRequest(
                query = searchProductRequest.query
            )
        )
    }

    override suspend fun getProductReview(productId: String): List<ProductReviewResponse> {
        return customerService.getProductReviews(productId)
    }

    override suspend fun addToCart(token: String,productId: String): CartResponse {
        return customerService.addToCart(token,productId)
    }

    override suspend fun getCartProducts(token: String): CartResponse {
        return customerService.getCartProducts(token)
    }

    override suspend fun deleteProductFromCart(token: String, productId: String): CartResponse {
        return customerService.deleteProductFromCart(token,productId)
    }

    override suspend fun getAllOrders(@Header("Authorization") token:String): List<Order> {
        return customerService.getAllOrders(token)
    }

    override suspend fun createOrder(createOrdersRequest: CreateOrdersRequest): OrdersResponse {
        return customerService.createOrder(
            CreateOrdersRequest(
                customerId=createOrdersRequest.customerId,
                email = createOrdersRequest.email,
                mobile = createOrdersRequest.mobile,
                products = createOrdersRequest.products
            )
        )
    }

    override suspend fun addToWishlist(token: String, productId: String): ProductFilterResponse {
        return customerService.addWishlist(token,productId)
    }

    override suspend fun getWishListProducts(token: String): ProductFilterResponse {
        return customerService.getWishListProducts(token)
    }

    override suspend fun deleteProductFromWishList(
        token: String,
        productId: String
    ): ProductFilterResponse {
        return customerService.deleteProductFromWishList(token,productId)
    }

    override suspend fun getOrderByOrderNo(token: String,orderNo: String): Order {
        return customerService.getOrderByOrderNo(token,orderNo)
    }

    override suspend fun updateOrder(token: String, orderId: String,updateOrderRequest: UpdateOrderRequest):Order {
        return customerService.updateOrder(token,orderId,UpdateOrderRequest(
            email = updateOrderRequest.email,
            status = updateOrderRequest.status
        ))
    }

    override suspend fun addReview(data: OrderReviewRequest): Response<Any> {
        return customerService.addReview(data)
    }
}