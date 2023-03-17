package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.orders.Order
import com.tasks.ecommerceapp.data.model.customer.orders.OrderProductsItem
import com.tasks.ecommerceapp.data.model.customer.orders.OrdersResponse
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
){
    suspend operator fun invoke(token:String): ProductsResults<List<Order>> {
        return customerRepository.getAllOrders(token)
    }
}