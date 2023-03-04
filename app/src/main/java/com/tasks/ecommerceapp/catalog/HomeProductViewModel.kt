package com.tasks.ecommerceapp.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceapp.api.CustomerRepository
import com.tasks.ecommerceapp.customer.catalog.CatalogResponse
import com.tasks.ecommerceapp.customer.product.ProductResponse
import com.tasks.ecommerceapp.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeProductViewModel @Inject constructor(private val repository: CustomerRepository) : ViewModel() {
    private val _catalogsLiveData = MutableLiveData<Results<List<CatalogResponse>>>()
    val catalogsLiveData: LiveData<Results<List<CatalogResponse>>> = _catalogsLiveData

    private val _productsLiveData = MutableLiveData<Results<List<ProductResponse>>>()
    val productsLiveData: LiveData<Results<List<ProductResponse>>> = _productsLiveData

    fun getCatalogs() {
        viewModelScope.launch {
            _catalogsLiveData.value = repository.getCatalog()
        }
    }

    fun getAllProducts(){
        viewModelScope.launch {
            _productsLiveData.postValue(repository.getAllProducts())
        }
    }

}
