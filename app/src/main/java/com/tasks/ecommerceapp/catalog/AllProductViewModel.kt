package com.tasks.ecommerceapp.catalog

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.tasks.ecommerceapp.api.CustomerRepository
import com.tasks.ecommerceapp.customer.product.ProductsItem
import com.tasks.ecommerceapp.customer.product.SearchProductResponse
import com.tasks.ecommerceapp.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllProductViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {


    private val _searchedProductsLiveData = MutableLiveData<Results<List<SearchProductResponse>>>()
    val searchedProductsLiveData: LiveData<Results<List<SearchProductResponse>>> = _searchedProductsLiveData

    suspend fun getFilteredProducts(
        color: String?,
        size: String?,
        categories: String?,
        sort: String?
    ): LiveData<PagingData<ProductsItem>> {
        return repository.getFilteredProducts(color, size, categories, sort)
                .asLiveData(viewModelScope.coroutineContext)
    }


    fun getSearchedProducts(searchedText:String){
        viewModelScope.launch {
            val searchedProducts=repository.getSearchedProducts(searchedText)
            _searchedProductsLiveData.postValue(searchedProducts)
        }
    }
}