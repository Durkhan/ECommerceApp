package com.tasks.ecommerceapp.presentation.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.data.model.customer.orders.GetAllOrdersResponse
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val repository: CustomerRepository) :
    ViewModel() {

    private var _ordersLiveData: MutableLiveData<List<GetAllOrdersResponse>>? =
        MutableLiveData<List<GetAllOrdersResponse>>()

    val ordersLiveData: LiveData<List<GetAllOrdersResponse>> get() = _ordersLiveData!!

    fun getAllOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllOrders()

            if (response.isSuccessful && response.body() != null) {
                _ordersLiveData?.value = response.body()
            }
        }
    }

    fun onDestroyView() {
        _ordersLiveData = null
    }

}