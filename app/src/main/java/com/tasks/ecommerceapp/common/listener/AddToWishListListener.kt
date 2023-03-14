package com.tasks.ecommerceapp.common.listener

import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem

interface AddToWishListListener {
    fun addToWishList(productItem:ProductsItem)
}