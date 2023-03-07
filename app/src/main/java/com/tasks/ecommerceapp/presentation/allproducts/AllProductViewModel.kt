package com.tasks.ecommerceapp.presentation.allproducts

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.data.model.customer.product.SearchProductResponse
import com.tasks.ecommerceapp.domain.usecases.GetFilteringProductsUseCase
import com.tasks.ecommerceapp.domain.usecases.GetSearchedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllProductViewModel @Inject constructor(
    private val getFilteringProductsUseCase: GetFilteringProductsUseCase,
    private val getSearchedProductsUseCase: GetSearchedProductsUseCase
) : ViewModel() {


    private val _searchedProductsLiveData = MutableLiveData<ProductsResults<List<SearchProductResponse>>>()
    val searchedProductsLiveData: LiveData<ProductsResults<List<SearchProductResponse>>> = _searchedProductsLiveData


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
}