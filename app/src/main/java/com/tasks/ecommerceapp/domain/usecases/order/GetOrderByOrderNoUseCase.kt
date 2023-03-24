package com.tasks.ecommerceapp.domain.usecases.order

import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.orders.Order
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class GetOrderByOrderNoUseCase @Inject constructor(
    private val customerRepository:CustomerRepository
) {
    suspend operator fun invoke(token:String,orderNo:String):ProductsResults<Order> {
        return customerRepository.getOrderByOrderNo(token,orderNo)
    }
}