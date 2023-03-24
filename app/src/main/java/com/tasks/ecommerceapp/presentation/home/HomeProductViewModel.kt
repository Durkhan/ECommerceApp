package com.tasks.ecommerceapp.presentation.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.catalog.CatalogResponse
import com.tasks.ecommerceapp.data.model.customer.product.ProductResponse
import com.tasks.ecommerceapp.data.model.customer.product.SearchProductResponse
import com.tasks.ecommerceapp.domain.usecases.product.GetAllProductsUseCase
import com.tasks.ecommerceapp.domain.usecases.product.GetCatalogUseCases
import com.tasks.ecommerceapp.domain.usecases.product.GetSearchedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeProductViewModel @Inject constructor(
    private val getCatalogUseCases: GetCatalogUseCases,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getSearchedProductsUseCase: GetSearchedProductsUseCase
    ) : ViewModel() {

    private val _catalogsLiveData = MutableLiveData<ProductsResults<List<CatalogResponse>>>()
    val catalogsLiveData: LiveData<ProductsResults<List<CatalogResponse>>> = _catalogsLiveData

    private val _productsLiveData = MutableLiveData<ProductsResults<List<ProductResponse>>>()
    val productsLiveData: LiveData<ProductsResults<List<ProductResponse>>> = _productsLiveData

    private val _searchedProductsLiveData = MutableLiveData<ProductsResults<List<SearchProductResponse>>>()
    val searchedProductsLiveData: LiveData<ProductsResults<List<SearchProductResponse>>> = _searchedProductsLiveData

    fun getCatalogs() {
        viewModelScope.launch {
            val result=getCatalogUseCases()
            _catalogsLiveData.postValue(result)
        }
    }


    fun getAllProducts(){
        viewModelScope.launch {
            val result=getAllProductsUseCase()
            _productsLiveData.postValue(result)
        }
    }

    fun getSearchedProducts(searchedText:String){
        viewModelScope.launch {
            val searchedProducts=getSearchedProductsUseCase(searchedText)
            _searchedProductsLiveData.postValue(searchedProducts)
        }
    }

}
