package com.tasks.ecommerceapp.presentation.product_review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.data.model.customer.product.ReviewsForProductResponse
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val repository: CustomerRepository) :
    ViewModel() {

    private var _productReviewsLiveData: MutableLiveData<ReviewsForProductResponse>? =
        MutableLiveData<ReviewsForProductResponse>()

    val productReviewsLiveData: LiveData<ReviewsForProductResponse> get() = _productReviewsLiveData!!

    fun getReviewsForProduct(productId: String) {
        viewModelScope.launch {
            val response = repository.getReviewsForProduct(productId)
            if (response.isSuccessful && response.body() != null) {
                _productReviewsLiveData?.value = response.body()
            }
        }
    }

    fun onDestroyView() {
        _productReviewsLiveData = null
    }

}