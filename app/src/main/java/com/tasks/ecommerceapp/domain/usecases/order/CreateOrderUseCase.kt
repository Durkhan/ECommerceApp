package com.tasks.ecommerceapp.domain.usecases.order

import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.cart.CartProductsItem
import com.tasks.ecommerceapp.data.model.customer.orders.OrdersResponse
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private var customerRepository: CustomerRepository
)  {
    suspend operator fun invoke(customerId: String,email: String,mobile:String,productsItem:List<CartProductsItem>):ProductsResults<OrdersResponse>{
        return customerRepository.createOrder(customerId,email,mobile,productsItem)
    }
}