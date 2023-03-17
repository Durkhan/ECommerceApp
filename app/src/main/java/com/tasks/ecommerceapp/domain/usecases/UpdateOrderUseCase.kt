package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.orders.Order
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(token:String,orderId:String,email:String,shipping:String):ProductsResults<Order>{
        return customerRepository.updateOrder(token,orderId,email,shipping)
    }
}