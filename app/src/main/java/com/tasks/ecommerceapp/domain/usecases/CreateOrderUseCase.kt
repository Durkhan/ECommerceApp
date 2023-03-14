package com.tasks.ecommerceapp.domain.usecases

import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.orders.GetAllOrdersResponse
import com.tasks.ecommerceapp.data.model.customer.orders.ProductsItemRequest
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private var customerRepository: CustomerRepository
)  {
    suspend operator fun invoke(token: String,email: String,mobile:String,productsItem: ProductsItemRequest):ProductsResults<GetAllOrdersResponse>{
        return customerRepository.createOrder(token,email,mobile,productsItem)
    }
}