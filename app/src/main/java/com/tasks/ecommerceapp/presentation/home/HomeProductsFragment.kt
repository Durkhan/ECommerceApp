package com.tasks.ecommerceapp.presentation.home
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.EmptyTextWatcher
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment
import com.tasks.ecommerceapp.data.model.customer.catalog.CatalogResponse
import com.tasks.ecommerceapp.databinding.FragmentHomeProductsBinding
import com.tasks.ecommerceapp.data.model.customer.product.ProductResponse
import com.tasks.ecommerceapp.presentation.adapter.CategoryAdapter
import com.tasks.ecommerceapp.presentation.adapter.ProductsinHomeAdapter
import com.tasks.ecommerceapp.common.Results
import com.tasks.ecommerceapp.data.model.customer.product.SearchProductResponse
import com.tasks.ecommerceapp.presentation.adapter.SearchedProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeProductsFragment : BaseViewBindingFragment<FragmentHomeProductsBinding>() {

    override fun getViewBinding() = FragmentHomeProductsBinding.inflate(layoutInflater)
    private val viewModel: HomeProductViewModel by viewModels()

    private lateinit var searchedProductsAdapter: SearchedProductsAdapter
    private var searchedProducts= listOf<SearchProductResponse>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          getCatalog()
          getAllProducts()
        binding.etSearch.addTextChangedListener(object : EmptyTextWatcher(){
            override fun onTextChanged(searched: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchProducts(searched.toString())
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback {
           requireActivity().finishAffinity()
        }
        binding.tvSeeAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeProductsFragment_to_allProductsFragment)
        }
    }



    private fun getAllProducts() {
        viewModel.getAllProducts()
        viewModel.productsLiveData.observe(viewLifecycleOwner){result->
            when(result){
                is ProductsResults.Loading ->{
                    binding.progressbar.isVisible=result.isLoading
                }
                is ProductsResults.Success ->{
                    val products=result.data
                    setProductsRecycleView(products)
                    binding.progressbar.isVisible=false
                }
                is ProductsResults.Error -> {
                  Log.d("ResultError",result.exception)
                }
            }

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
                        binding.rvAllProducts.visibility=View.GONE
                        binding.constraint.visibility=View.VISIBLE
                    }
                    else{
                        binding.rvAllProducts.visibility=View.VISIBLE
                        binding.constraint.visibility=View.GONE
                        searchedProducts=result.data
                        searchedProductsAdapter= SearchedProductsAdapter(searchedProducts)
                        binding.rvAllProducts.adapter=searchedProductsAdapter
                    }
                }
                is ProductsResults.Error ->{
                    Log.d("Data",""+result.exception)
                }
            }

        }
    }

    private fun setProductsRecycleView(products: List<ProductResponse>) {
        val layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvProducts.layoutManager =layoutManager
        val adapter = ProductsinHomeAdapter(products)
        binding.rvProducts.adapter = adapter
    }

    private fun getCatalog() {
        viewModel.getCatalogs()
        viewModel.catalogsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ProductsResults.Loading ->{
                    binding.progressbar.isVisible=result.isLoading
                }
                is ProductsResults.Success -> {
                    val categories=result.data
                    catalogRecyclerview(categories)
                }
                is ProductsResults.Error -> {
                    Log.d("ResultError",result.exception)
                }
            }
        }

    }

    private fun catalogRecyclerview(categories: List<CatalogResponse>) {
        val layoutManager = GridLayoutManager(context,2, RecyclerView.HORIZONTAL,false)
        binding.rvShopCategories.layoutManager = layoutManager
        val adapter = CategoryAdapter(categories)
        binding.rvShopCategories.adapter = adapter
    }
}