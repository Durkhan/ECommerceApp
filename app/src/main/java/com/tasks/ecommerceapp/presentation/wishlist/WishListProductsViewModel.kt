package com.tasks.ecommerceapp.presentation.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.product.ProductFilterResponse
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.domain.usecases.DeleteProductFromWishListUseCases
import com.tasks.ecommerceapp.domain.usecases.GetWishListProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListProductsViewModel @Inject constructor(
    private val getWishListProductsUseCase: GetWishListProductsUseCase,
    private val deleteProductFromWishListUseCases: DeleteProductFromWishListUseCases,
    private val dataStoreManager: DataStoreManager
):ViewModel() {

    private val _getWishListLivedata=MutableLiveData<ProductsResults<ProductFilterResponse>>()
    val getWishListLivedata:LiveData<ProductsResults<ProductFilterResponse>> = _getWishListLivedata

    private val _deleteProductFromWishListLivedata=MutableLiveData<ProductsResults<ProductFilterResponse>>()
    val deleteProductFromWishListLivedata:LiveData<ProductsResults<ProductFilterResponse>> = _deleteProductFromWishListLivedata


    fun getWishlistProducts(){
        viewModelScope.launch {
            val result=getWishListProductsUseCase(dataStoreManager.token.first())
            _getWishListLivedata.postValue(result)
        }
    }

    fun deleteProductFromWishList(productsId: String){
        viewModelScope.launch {
            val result=deleteProductFromWishListUseCases(dataStoreManager.token.first(),productsId)
            _deleteProductFromWishListLivedata.postValue(result)
        }
    }
}