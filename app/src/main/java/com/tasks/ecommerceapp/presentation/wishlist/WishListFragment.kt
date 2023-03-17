package com.tasks.ecommerceapp.presentation.wishlist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.common.listener.DeleteProductFromWishListListener
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.databinding.FragmentWishlistBinding
import com.tasks.ecommerceapp.presentation.adapter.WishListAdapter
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishListFragment: BaseViewBindingFragment<FragmentWishlistBinding>(),DeleteProductFromWishListListener{
    override fun getViewBinding()=FragmentWishlistBinding.inflate(layoutInflater)

    private val viewModel:WishListProductsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.fragName.text=getString(R.string.my_wishList)
        getWishListProducts()

        binding.header.ibBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


    private fun getWishListProducts() {
        viewModel.getWishlistProducts()
        viewModel.getWishListLivedata.observe(viewLifecycleOwner){result->
            when(result){
                is ProductsResults.Loading ->{
                    binding.progressBar.isVisible=result.isLoading
                }
                is ProductsResults.Success ->{
                    val response=result.data.products
                    response?.let { initializeWishlist(it) }
                    binding.progressBar.isVisible=false
                }
                is ProductsResults.Error ->{
                    Log.d("ProductsResultsError",result.exception)
                }
            }
        }
    }

    private fun initializeWishlist(response: List<ProductsItem>) {
        val adapter = WishListAdapter(response,this@WishListFragment)
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        binding.rvWishlist.layoutManager = layoutManager
        binding.rvWishlist.adapter = adapter
    }

    override fun deleteProduct(productsItem: ProductsItem) {
        productsItem._id?.let { viewModel.deleteProductFromWishList(it) }
        viewModel.deleteProductFromWishListLivedata.observe(viewLifecycleOwner){result->
            when(result){
                is ProductsResults.Success ->{
                    result.data.products?.let { initializeWishlist(it) }
                }
                is ProductsResults.Error -> {
                    Log.d("ProductsResultsError",result.exception)
                }
                else -> {}
            }

        }
    }

}