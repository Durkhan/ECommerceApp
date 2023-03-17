package com.tasks.ecommerceapp.presentation.allproducts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment
import com.tasks.ecommerceapp.data.model.customer.product.SearchProductResponse
import com.tasks.ecommerceapp.databinding.FragmentAllProductsBinding
import com.tasks.ecommerceapp.presentation.adapter.AllProductsAdapter
import com.tasks.ecommerceapp.presentation.adapter.SearchedProductsAdapter
import com.tasks.ecommerceapp.common.*
import com.tasks.ecommerceapp.common.listener.AddToWishListListener
import com.tasks.ecommerceapp.common.listener.OnItemClickListener
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AllProductsFragment : BaseViewBindingFragment<FragmentAllProductsBinding>(),OnItemClickListener,AddToWishListListener {
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


        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }


        binding.ibHeart.setOnClickListener {
            findNavController().navigate(R.id.action_allProductsFragment_to_wishListFragment)
        }
    }

    private fun searchProducts(searched: String) {
        viewModel.getSearchedProducts(searched)
        viewModel.searchedProductsLiveData.observe(viewLifecycleOwner){result->
            when(result){
                is ProductsResults.Loading->{
                    binding.progressbar.visibility=View.VISIBLE
                }
                is ProductsResults.Success ->{
                    if (searched.isBlank()){
                        binding.progressbar.visibility=View.GONE
                        initAllProductsRecyclerView()
                    }
                    else{
                        searchedProducts=result.data
                        searchedProductsAdapter=SearchedProductsAdapter(searchedProducts)
                        binding.rvAllProducts.adapter=searchedProductsAdapter
                    }
                }
                is ProductsResults.Error ->{
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
        allProductsAdapter = AllProductsAdapter(
            requireContext(),
            this@AllProductsFragment,
            this@AllProductsFragment,
            AllProductsAdapter.DiffCallback()
        )
        lifecycleScope.launch {
            viewModel.getFilteredProducts(null, null, null, null)
                .asFlow().collect{pagingData->
                    allProductsAdapter.submitData(pagingData)
                    Log.d("pagingData",""+pagingData)
                }
        }
        rvAllProducts.adapter = allProductsAdapter
    }

    override fun onItemClick(productItem: ProductsItem) {
        val action=AllProductsFragmentDirections.actionAllProductsFragmentToProductDetailFragment(productItem)
        findNavController().navigate(action)
    }

    override fun addToWishList(productItem: ProductsItem) {
        viewModel.addToWishList(productItem._id.toString())
        viewModel.addToWishListLiveData.observe(viewLifecycleOwner){result->
               when(result){
                   is ProductsResults.Success ->{
                       Toast.makeText(requireContext(),getString(R.string.succesfully_added),Toast.LENGTH_LONG).show()
                   }
                   is ProductsResults.Error ->{
                       Log.d("WisHList",result.exception.toString())
                   }
                   else -> {}
               }
        }
    }

}