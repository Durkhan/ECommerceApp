package com.tasks.ecommerceapp.presentation.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.databinding.FragmentCardBinding
import com.tasks.ecommerceapp.common.ProductCheckOutData
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.cart.CartProductsItem
import com.tasks.ecommerceapp.data.model.customer.cart.toRequest
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment
import com.tasks.ecommerceapp.presentation.adapter.ProductCheckoutAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardFragment : BaseViewBindingFragment<FragmentCardBinding>(){

    private val productsAdapter = ProductCheckoutAdapter()
    private val cardProductsViewModel:CartProductsViewModel by viewModels()
    private val checkedoutedProductList= mutableListOf<CartProductsItem>()
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
               val selectedProducts = productsAdapter.selectedProducts()
                selectedProducts.forEach{
                    it.product.product?._id?.let { it ->
                        cardProductsViewModel.deleteProductFromCart(it)
                    }
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
        createOrders()

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
    private fun createOrders() {

        binding.btnAddToCard.setOnClickListener {
           var selectedList:List<ProductCheckOutData> = productsAdapter.selectedProducts()
             for (product in selectedList){
                 checkedoutedProductList.add(product.product)
             }
            cardProductsViewModel.createOrder(
                cardProductsViewModel.customerId,
                cardProductsViewModel.email,
                cardProductsViewModel.mobile,
                checkedoutedProductList
            )

            cardProductsViewModel.orders.observe(viewLifecycleOwner){result->
                when(result){
                    is ProductsResults.Success ->{
                        Toast.makeText(requireContext(),"buy succesfully", Toast.LENGTH_LONG).show()
                        Log.d("OrdersErrorNumer",""+result.data)
                    }

                    is ProductsResults.Error ->{
                        Log.d("OrdersError",result.exception)
                    }
                }
            }
        }
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
                        product?.let { ProductCheckOutData(it) }?.let { productList.add(it) }
                    productsAdapter.submitData(productList)
                    binding.progressBar.isVisible=false

                    cardProductsViewModel.email=result.data.customerId?.email.toString()
                    cardProductsViewModel.mobile=result.data.customerId?.telephone.toString()
                    cardProductsViewModel.customerId=result.data.customerId?._id.toString()
                }

                is ProductsResults.Error ->{
                    Log.d("ProductsResultsError ",result.exception)
                }
            }
        }
    }



}