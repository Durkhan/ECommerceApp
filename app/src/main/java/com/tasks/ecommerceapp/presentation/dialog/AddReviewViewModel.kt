package com.tasks.ecommerceapp.presentation.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.review.OrderReviewRequest
import com.tasks.ecommerceapp.data.model.customer.review.ProductReviewResponse
import com.tasks.ecommerceapp.domain.usecases.review.AddReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val addReviewUseCase: AddReviewUseCase,
    private val dataStoreManager: DataStoreManager
    ) : ViewModel() {

    private val _addReviewLiveData=MutableLiveData<ProductsResults<ProductReviewResponse>>()
    val addReviewLiveData: LiveData<ProductsResults<ProductReviewResponse>> = _addReviewLiveData

    fun submitReview(data: OrderReviewRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = addReviewUseCase(dataStoreManager.token.first(),data)
            _addReviewLiveData.postValue(response)
        }
    }

}