package com.tasks.ecommerceapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.tasks.ecommerceapp.base.BaseViewBindingFragment
import com.tasks.ecommerceapp.databinding.FragmentCardBinding
import com.tasks.ecommerceapp.model.ProductCheckOutData
import com.tasks.ecommerceapp.ui.adapter.ProductCheckoutAdapter

class CardFragment : BaseViewBindingFragment<FragmentCardBinding>() {

    private val productsAdapter = ProductCheckoutAdapter()

    override fun getViewBinding() = FragmentCardBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvProducts.adapter = productsAdapter
        productsAdapter.submitData(
            listOf(
                ProductCheckOutData("a xiaomi", 5.40),
                ProductCheckOutData("b samsung", 1.20),
                ProductCheckOutData("c nokia", 1.30),
                ProductCheckOutData("d iphone", 1.90)
            )
        )
        binding.ibDelete.isEnabled = false

        productsAdapter.productDeleteEvent = { selected ->
            requireActivity().runOnUiThread {
                binding.ibDelete.isEnabled = selected
            }
        }

        productsAdapter.productsTotalPriceEvent = {
            requireActivity().runOnUiThread {
                binding.clCheckout.isVisible = it != 0.0
                binding.tvPrice.text = it.toString()
            }
        }

        binding.ibDelete.setOnClickListener {
            if (binding.ibDelete.isEnabled) {
                productsAdapter.removeSelectedProducts()
            }
        }

    }

}