package com.tasks.ecommerceapp.presentation.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.data.model.customer.orders.OrderReviewRequest
import com.tasks.ecommerceapp.data.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(private val repository: CustomerRepository) : ViewModel() {

    val addReviewLiveData: MutableLiveData<String> = MutableLiveData()

    fun submitReview(data: OrderReviewRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.addReview(data)
            if (response.isSuccessful) {
                addReviewLiveData.postValue(response.message())
            }

        }
    }

}