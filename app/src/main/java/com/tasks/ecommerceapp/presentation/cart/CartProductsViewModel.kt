package com.tasks.ecommerceapp.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.cart.CartProductsItem
import com.tasks.ecommerceapp.data.model.customer.cart.CartResponse
import com.tasks.ecommerceapp.data.model.customer.orders.OrdersResponse
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.domain.usecases.CreateOrderUseCase
import com.tasks.ecommerceapp.domain.usecases.DeleteProductFromCartUseCases
import com.tasks.ecommerceapp.domain.usecases.GetCartAllProductsUseCase
import com.tasks.ecommerceapp.domain.usecases.GetCustomerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartProductsViewModel @Inject constructor(
    private val getCartAllProductsUseCase: GetCartAllProductsUseCase,
    private val deleteProductFromCartUseCases: DeleteProductFromCartUseCases,
    private val createOrderUseCase: CreateOrderUseCase,
    private val dataStoreManager: DataStoreManager
):ViewModel() {

    var email:String=""
    var mobile:String=""
    var customerId:String=""

    private val _cartProductsLivedata=MutableLiveData<ProductsResults<CartResponse>>()
    val cartProductsLivedata:LiveData<ProductsResults<CartResponse>> = _cartProductsLivedata

    private val _deleteProductFromCartLivedata=MutableLiveData<ProductsResults<CartResponse>>()
    val deleteProductFromCartLivedata:LiveData<ProductsResults<CartResponse>> = _deleteProductFromCartLivedata

    private val _orders = MutableLiveData<ProductsResults<OrdersResponse>>()
    val orders: LiveData<ProductsResults<OrdersResponse>> = _orders

    fun getCartProducts(){
        viewModelScope.launch {
           val result=getCartAllProductsUseCase(dataStoreManager.token.first())
           _cartProductsLivedata.postValue(result)
        }
    }


    fun deleteProductFromCart(productId:String){
        viewModelScope.launch {
            val result=deleteProductFromCartUseCases(dataStoreManager.token.first(),productId)
            _deleteProductFromCartLivedata.postValue(result)
        }
    }

    fun createOrder(customerId:String,email:String,mobile:String,productsItem:List<CartProductsItem>){
        viewModelScope.launch {
            val result=createOrderUseCase(customerId,email,mobile,productsItem)
            _orders.postValue(result)
        }
    }
}