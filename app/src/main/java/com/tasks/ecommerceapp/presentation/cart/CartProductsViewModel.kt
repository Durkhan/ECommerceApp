package com.tasks.ecommerceapp.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.cart.CartResponse
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.domain.usecases.DeleteProductFromCartUseCases
import com.tasks.ecommerceapp.domain.usecases.GetCartAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartProductsViewModel @Inject constructor(
    private val getCartAllProductsUseCase: GetCartAllProductsUseCase,
    private val deleteProductFromCartUseCases: DeleteProductFromCartUseCases,
    private val dataStoreManager: DataStoreManager
):ViewModel() {
    private val _cartProductsLivedata=MutableLiveData<ProductsResults<CartResponse>>()
    val cartProductsLivedata:LiveData<ProductsResults<CartResponse>> = _cartProductsLivedata

    private val _deleteProductFromCartLivedata=MutableLiveData<ProductsResults<CartResponse>>()
    val deleteProductFromCartLivedata:LiveData<ProductsResults<CartResponse>> = _deleteProductFromCartLivedata

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
}