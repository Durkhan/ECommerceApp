package com.tasks.ecommerceapp.presentation.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.databinding.FragmentCardBinding
import com.tasks.ecommerceapp.common.ProductCheckOutData
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment
import com.tasks.ecommerceapp.presentation.adapter.ProductCheckoutAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardFragment : BaseViewBindingFragment<FragmentCardBinding>() {

    private val productsAdapter = ProductCheckoutAdapter()
    private val cardProductsViewModel:CartProductsViewModel by viewModels()
    override fun getViewBinding() = FragmentCardBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvProducts.adapter = productsAdapter
        binding.ibDelete.isEnabled = false

        productsAdapter.productDeleteEvent = { selected ->
            requireActivity().runOnUiThread {
                binding.ibDelete.isEnabled = selected
            }
        }

        productsAdapter.productsTotalPriceEvent = {
            requireActivity().runOnUiThread {
                binding.clCheckout.isVisible = it != 0.0
                binding.tvPrice.text ="US $$it"
            }
        }


        binding.ibDelete.setOnClickListener {
            if (binding.ibDelete.isEnabled) {
               val selectedProducts = productsAdapter.removeSelectedProducts()
                selectedProducts.forEach{
                        cardProductsViewModel.deleteProductFromCart(it.productId)
                }
                cardProductsViewModel.deleteProductFromCartLivedata.observe(viewLifecycleOwner){result->
                    when(result){
                        is ProductsResults.Success ->{
                           getCartProducts()
                        }
                        else -> {}
                    }
                }
            }
        }

        getCartProducts()

        binding.ibReturnBack.setOnClickListener {
            clickBackPressed()
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            clickBackPressed()
        }
    }

    private fun clickBackPressed() {
        findNavController().popBackStack()
    }

    private fun getCartProducts() {
        cardProductsViewModel.getCartProducts()
        cardProductsViewModel.cartProductsLivedata.observe(viewLifecycleOwner){result ->
            when(result){
                is ProductsResults.Loading ->{
                    binding.progressBar.isVisible=result.isLoading
                }

                is ProductsResults.Success -> {
                    val productList= mutableListOf<ProductCheckOutData>()
                    for (product in result.data.products!!)
                        productList.add(ProductCheckOutData(
                            product?.product?.name.toString(),
                            product?.product?.currentPrice!!.toDouble(),
                            product.product.imageUrls?.get(0)!!,
                            product.product._id.toString()
                        ))
                    productsAdapter.submitData(productList)
                    binding.progressBar.isVisible=false
                }

                is ProductsResults.Error ->{
                    Log.d("ProductsResultsError ",result.exception)
                }
            }
        }
    }

}