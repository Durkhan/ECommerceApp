package com.tasks.ecommerceapp.presentation.allproducts

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tasks.ecommerceapp.data.api.CustomerService
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem

class ProductFilterPagingSource(
    private val service: CustomerService,
    private val color: String?,
    private val size: String?,
    private val categories: String?,
    private val sort: String?
) : PagingSource<Int, ProductsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,ProductsItem> {
        val page = params.key ?: 1
        val perPage = params.loadSize


        return try {
            val response = service.getFilteredProducts(color, size, categories, perPage, page, sort)
            val products = response.products ?: emptyList()
            LoadResult.Page(
                data = products,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (products.isNotEmpty()) page + 1 else null,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

