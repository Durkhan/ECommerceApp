package com.tasks.ecommerceapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.tasks.ecommerceapp.base.BaseViewBindingFragment
import com.tasks.ecommerceapp.databinding.FragmentAllProductsBinding
import com.tasks.ecommerceapp.ui.adapter.AllProductsAdapter
import com.tasks.ecommerceapp.utils.ProductListType


class AllProductsFragment : BaseViewBindingFragment<FragmentAllProductsBinding>() {

    private val allProductsAdapter = AllProductsAdapter()

    override fun getViewBinding() = FragmentAllProductsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initProductsFilterEvents()
    }

    private fun initProductsFilterEvents() = with(binding) {

        ibProductsGroup.setOnClickListener {
            (rvAllProducts.layoutManager as GridLayoutManager).spanCount = 1
            allProductsAdapter.setProductType(ProductListType.GROUP_TYPE)
        }

        ibProductsList.setOnClickListener {
            (rvAllProducts.layoutManager as GridLayoutManager).spanCount = 2
            allProductsAdapter.setProductType(ProductListType.LIST_TYPE)
        }

    }

    private fun initRecyclerView() = with(binding) {
        rvAllProducts.adapter = allProductsAdapter
        allProductsAdapter.submitList(listOf("1", "2", "3", "4", "5"))
    }

}