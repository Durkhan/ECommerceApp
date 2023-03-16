package com.tasks.ecommerceapp.presentation.allproducts


import androidx.lifecycle.*
import androidx.paging.PagingData
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.common.Results
import com.tasks.ecommerceapp.data.model.customer.cart.CartResponse
import com.tasks.ecommerceapp.data.model.customer.orders.GetAllOrdersResponse
import com.tasks.ecommerceapp.data.model.customer.orders.ProductsItemRequest
import com.tasks.ecommerceapp.data.model.customer.product.ProductFilterResponse
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.data.model.customer.product.SearchProductResponse
import com.tasks.ecommerceapp.data.model.customer.register.CustomerResponse
import com.tasks.ecommerceapp.data.model.customer.review.ProductReviewResponse
import com.tasks.ecommerceapp.domain.usecases.*
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
    private val createOrderUseCase: CreateOrderUseCase,
    private val getCustomerUseCase: GetCustomerUseCase,
    private val addToWishListUseCase: AddToWishListUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {


    private val _searchedProductsLiveData = MutableLiveData<ProductsResults<List<SearchProductResponse>>>()
    val searchedProductsLiveData: LiveData<ProductsResults<List<SearchProductResponse>>> = _searchedProductsLiveData

    private val _reviewsLiveData = MutableLiveData<ProductsResults<List<ProductReviewResponse>>>()
    val reviewsLiveData: LiveData<ProductsResults<List<ProductReviewResponse>>> = _reviewsLiveData

    private val _addToCartLiveData = MutableLiveData<ProductsResults<CartResponse>>()
    val addToCartLiveData: LiveData<ProductsResults<CartResponse>> = _addToCartLiveData

    private val _customer = MutableLiveData<Results<CustomerResponse>>()
    val customer: LiveData<Results<CustomerResponse>> = _customer

    private val _orders = MutableLiveData<ProductsResults<GetAllOrdersResponse>>()
    val orders: LiveData<ProductsResults<GetAllOrdersResponse>> = _orders

    private val _addToWishListLiveData = MutableLiveData<ProductsResults<ProductFilterResponse>>()
    val addToWishListLiveData: LiveData<ProductsResults<ProductFilterResponse>> = _addToWishListLiveData

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

    fun createOrder(email:String,mobile:String,productsItem: ProductsItemRequest){
        viewModelScope.launch {
            val result=createOrderUseCase(dataStoreManager.token.first(),email,mobile,productsItem)
            _orders.postValue(result)
        }
    }

    fun getCustomer() {
        viewModelScope.launch {
            val result=getCustomerUseCase(dataStoreManager.token.first())
            _customer.postValue(result)
        }
    }

    fun addToWishList(productId: String){
        viewModelScope.launch {
            val result=addToWishListUseCase(dataStoreManager.token.first(),productId)
            _addToWishListLiveData.postValue(result)
        }
    }

}