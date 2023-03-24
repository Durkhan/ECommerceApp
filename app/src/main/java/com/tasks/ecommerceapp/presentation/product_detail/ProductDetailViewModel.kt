package com.tasks.ecommerceapp.presentation.product_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.cart.CartResponse
import com.tasks.ecommerceapp.data.model.customer.product.ProductFilterResponse
import com.tasks.ecommerceapp.data.model.customer.review.ProductReviewResponse
import com.tasks.ecommerceapp.domain.usecases.cart.AddToCartUseCase
import com.tasks.ecommerceapp.domain.usecases.wishlist.AddToWishListUseCase
import com.tasks.ecommerceapp.domain.usecases.review.GetProductReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private var addToCartUseCase: AddToCartUseCase,
    private var getProductReviewsUseCase: GetProductReviewsUseCase,
    private var addToWishListUseCase: AddToWishListUseCase,
    private var dataStoreManager: DataStoreManager
):ViewModel() {

    private val _addToCartLiveData = MutableLiveData<ProductsResults<CartResponse>>()
    val addToCartLiveData: LiveData<ProductsResults<CartResponse>> = _addToCartLiveData

    private val _reviewsLiveData = MutableLiveData<ProductsResults<List<ProductReviewResponse>>>()
    val reviewsLiveData: LiveData<ProductsResults<List<ProductReviewResponse>>> = _reviewsLiveData

    private val _addToWishListLiveData = MutableLiveData<ProductsResults<ProductFilterResponse>>()
    val addToWishListLiveData: LiveData<ProductsResults<ProductFilterResponse>> = _addToWishListLiveData

    fun addToCard(productId: String){
        viewModelScope.launch {
            val result=addToCartUseCase(dataStoreManager.token.first(),productId)
            _addToCartLiveData.postValue(result)
        }
    }

    fun getProductReviews(productId:String){
        viewModelScope.launch {
            val result = getProductReviewsUseCase(productId)
            _reviewsLiveData.postValue(result)
        }
    }


    fun addToWishList(productId: String){
        viewModelScope.launch {
            val result=addToWishListUseCase(dataStoreManager.token.first(),productId)
            _addToWishListLiveData.postValue(result)
        }
    }

}