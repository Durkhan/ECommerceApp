package com.tasks.ecommerceapp.common

sealed class ProductsResults<out T> {
    data class Success<out T>(val data: T) : ProductsResults<T>()
    data class Loading<T>(val isLoading: Boolean) : ProductsResults<T>()
    data class Error(val exception: String) : ProductsResults<Nothing>()
}