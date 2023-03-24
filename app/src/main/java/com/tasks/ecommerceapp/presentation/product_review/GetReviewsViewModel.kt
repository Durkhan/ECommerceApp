package com.tasks.ecommerceapp.presentation.product_review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.review.ProductReviewResponse
import com.tasks.ecommerceapp.domain.usecases.review.GetProductReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetReviewsViewModel @Inject constructor(
    private var getProductReviewsUseCase: GetProductReviewsUseCase
):ViewModel() {

    private val _reviewsLiveData = MutableLiveData<ProductsResults<List<ProductReviewResponse>>>()
    val reviewsLiveData: LiveData<ProductsResults<List<ProductReviewResponse>>> = _reviewsLiveData

    fun getProductReviews(productId:String){
        viewModelScope.launch {
            val result = getProductReviewsUseCase(productId)
            _reviewsLiveData.postValue(result)
        }
    }
}