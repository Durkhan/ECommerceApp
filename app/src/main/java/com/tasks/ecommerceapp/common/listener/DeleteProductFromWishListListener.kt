package com.tasks.ecommerceapp.common.listener

import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem

interface DeleteProductFromWishListListener {
    fun deleteProduct(productsItem: ProductsItem)
}