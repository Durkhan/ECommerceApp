package com.tasks.ecommerceapp.presentation.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.orders.Order
import com.tasks.ecommerceapp.domain.usecases.order.GetAllOrdersUseCase
import com.tasks.ecommerceapp.domain.usecases.order.GetOrderByOrderNoUseCase
import com.tasks.ecommerceapp.domain.usecases.order.UpdateOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val getOrderByOrderNoUseCase: GetOrderByOrderNoUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase,
    private val dataStoreManager: DataStoreManager
    ) : ViewModel() {

    private var _ordersLiveData=MutableLiveData<ProductsResults<List<Order>>>()
    val ordersLiveData: LiveData<ProductsResults<List<Order>>> = _ordersLiveData

    private var _ordersOrderNoLiveData=MutableLiveData<ProductsResults<Order>>()
    val ordersOrderNoLiveData: LiveData<ProductsResults<Order>> = _ordersOrderNoLiveData

    private var _updateOrderLiveData=MutableLiveData<ProductsResults<Order>>()
    val updateOrderLiveData: LiveData<ProductsResults<Order>> = _updateOrderLiveData

    fun getAllOrders() {
        viewModelScope.launch() {
            val result=getAllOrdersUseCase(dataStoreManager.token.first())
            _ordersLiveData.postValue(result)
        }
    }

    fun getOrderByOrderNo(orderNo:String) {
        viewModelScope.launch() {
            val result=getOrderByOrderNoUseCase(dataStoreManager.token.first(),orderNo)
            _ordersOrderNoLiveData.postValue(result)
        }
    }

    fun updateOrder(orderId:String,email:String,status:String){
        viewModelScope.launch {
            val result = updateOrderUseCase(dataStoreManager.token.first(),orderId,email,status)
            _updateOrderLiveData.postValue(result)
        }
    }


}