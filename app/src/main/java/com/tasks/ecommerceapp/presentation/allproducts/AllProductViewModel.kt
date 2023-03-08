package com.tasks.ecommerceapp.presentation.allproducts

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.cart.CartProductsItem
import com.tasks.ecommerceapp.data.model.customer.cart.CartResponse
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.data.model.customer.product.SearchProductResponse
import com.tasks.ecommerceapp.data.model.customer.review.ProductReviewResponse
import com.tasks.ecommerceapp.domain.usecases.AddToCartUseCase
import com.tasks.ecommerceapp.domain.usecases.GetFilteringProductsUseCase
import com.tasks.ecommerceapp.domain.usecases.GetProductReviewsUseCase
import com.tasks.ecommerceapp.domain.usecases.GetSearchedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllProductViewModel @Inject constructor(
    private val getFilteringProductsUseCase: GetFilteringProductsUseCase,
    private val getSearchedProductsUseCase: GetSearchedProductsUseCase,
    private val getProductReviewsUseCase: GetProductReviewsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {


    private val _searchedProductsLiveData = MutableLiveData<ProductsResults<List<SearchProductResponse>>>()
    val searchedProductsLiveData: LiveData<ProductsResults<List<SearchProductResponse>>> = _searchedProductsLiveData

    private val _reviewsLiveData = MutableLiveData<ProductsResults<List<ProductReviewResponse>>>()
    val reviewsLiveData: LiveData<ProductsResults<List<ProductReviewResponse>>> = _reviewsLiveData

    private val _addToCartLiveData = MutableLiveData<ProductsResults<CartResponse>>()
    val addToCartLiveData: LiveData<ProductsResults<CartResponse>> = _addToCartLiveData

    suspend fun getFilteredProducts(
        color: String?,
        size: String?,
        categories: String?,
        sort: String?
    ):LiveData<PagingData<ProductsItem>>{
        return getFilteringProductsUseCase(color,size,categories,sort).asLiveData()
    }


    fun getSearchedProducts(searchedText:String){
        viewModelScope.launch {
            val searchedProducts=getSearchedProductsUseCase(searchedText)
            _searchedProductsLiveData.postValue(searchedProducts)
        }
    }



    fun getProductReviews(productId:String){
        viewModelScope.launch {
            val result = getProductReviewsUseCase(productId)
            _reviewsLiveData.postValue(result)
        }
    }


    fun addToCard(productId: String){
        viewModelScope.launch {
            val result=addToCartUseCase(dataStoreManager.token.first(),productId)
            _addToCartLiveData.postValue(result)
        }
    }


}