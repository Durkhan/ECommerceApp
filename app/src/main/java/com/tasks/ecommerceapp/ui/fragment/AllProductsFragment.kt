package com.tasks.ecommerceapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.tasks.ecommerceapp.base.BaseViewBindingFragment
import com.tasks.ecommerceapp.catalog.AllProductViewModel
import com.tasks.ecommerceapp.customer.product.SearchProductResponse
import com.tasks.ecommerceapp.databinding.FragmentAllProductsBinding
import com.tasks.ecommerceapp.ui.adapter.AllProductsAdapter
import com.tasks.ecommerceapp.ui.adapter.SearchedProductsAdapter
import com.tasks.ecommerceapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AllProductsFragment : BaseViewBindingFragment<FragmentAllProductsBinding>() {
    private lateinit var allProductsAdapter:AllProductsAdapter
    private lateinit var searchedProductsAdapter: SearchedProductsAdapter
    private var searchedProducts= listOf<SearchProductResponse>()
    private val viewModel: AllProductViewModel by viewModels()
    override fun getViewBinding() = FragmentAllProductsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initAllProductsRecyclerView()
        initProductsFilterEvents()

        binding.etSearch.addTextChangedListener(object :EmptyTextWatcher(){
            override fun onTextChanged(searched: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchProducts(searched.toString())
            }

        })



    }

    private fun searchProducts(searched: String) {
        viewModel.getSearchedProducts(searched)
        viewModel.searchedProductsLiveData.observe(viewLifecycleOwner){result->
            when(result){
                is Results.Success ->{
                    Log.d("Data",""+result.data)
                 if (searched.isBlank()){
                     initAllProductsRecyclerView()
                 }
                 else{
                     searchedProducts=result.data
                     searchedProductsAdapter=SearchedProductsAdapter(searchedProducts)
                     binding.rvAllProducts.adapter=searchedProductsAdapter
                 }
                }

                is Results.Error ->{
                    Log.d("Data",""+result.exception)

                }
            }

        }
    }

    private fun initProductsFilterEvents() = with(binding) {
        ibProductsGroup.setOnClickListener {
            (rvAllProducts.layoutManager as GridLayoutManager).spanCount = 1
            allProductsAdapter.setProductType(ProductListType.GROUP_TYPE)

            if (searchedProducts.isNotEmpty()) {
                searchedProductsAdapter.setProductType(ProductListType.GROUP_TYPE)
            }
        }

        ibProductsList.setOnClickListener {
            (rvAllProducts.layoutManager as GridLayoutManager).spanCount = 2
            allProductsAdapter.setProductType(ProductListType.LIST_TYPE)

            if (searchedProducts.isNotEmpty()){
                searchedProductsAdapter.setProductType(ProductListType.LIST_TYPE)
            }

        }

    }

    private fun initAllProductsRecyclerView() = with(binding) {
        allProductsAdapter=AllProductsAdapter(
            requireContext(),
            AllProductsAdapter.DiffCallback()
        )
        lifecycleScope.launch {
            viewModel.getFilteredProducts(
                color = null,
                size = null,
                categories = null,
                sort = null
            ).asFlow().collectLatest { pagingData ->
                allProductsAdapter.submitData(pagingData)
            }
        }
        rvAllProducts.adapter = allProductsAdapter
    }

}