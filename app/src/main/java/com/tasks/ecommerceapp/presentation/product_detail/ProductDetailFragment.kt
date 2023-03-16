package com.tasks.ecommerceapp.presentation.product_detail

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.common.Results
import com.tasks.ecommerceapp.common.listener.AddToWishListListener
import com.tasks.ecommerceapp.common.listener.OnItemClickListener
import com.tasks.ecommerceapp.common.listener.SimilarItemClickListener
import com.tasks.ecommerceapp.data.model.customer.orders.toRequest
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.databinding.FragmentProductDetailBinding
import com.tasks.ecommerceapp.extensions.getCurrentPosition
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment
import com.tasks.ecommerceapp.presentation.adapter.ImgSliderAdapter
import com.tasks.ecommerceapp.presentation.adapter.ProductDetailImagesAdapter
import com.tasks.ecommerceapp.presentation.adapter.SimilarProductsAdapter
import com.tasks.ecommerceapp.presentation.allproducts.AllProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : BaseViewBindingFragment<FragmentProductDetailBinding>(),
    OnItemClickListener,SimilarItemClickListener,AddToWishListListener {

    private val imgSliderAdapter = ImgSliderAdapter()
    private lateinit var similarProductsAdapter:SimilarProductsAdapter
    private val args:ProductDetailFragmentArgs by navArgs()
    override fun getViewBinding() = FragmentProductDetailBinding.inflate(layoutInflater)
    private val viewModel:AllProductViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productItem=args.productsItem!!
        assignProductDetails(productItem)

        binding.ibReturnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }

        createOrders(productItem)


    }

    private fun createOrders(productItem: ProductsItem) {
        val productsItemRequest=productItem.toRequest()
        binding.btnBuyNow.setOnClickListener {
            viewModel.getCustomer()
            viewModel.customer.observe(viewLifecycleOwner){result->
                when(result){
                    is Results.Success ->{
                        val response=result.data
                       viewModel.createOrder(
                           response.email.toString(),
                           response.telephone.toString(),
                           productsItemRequest
                       )
                }
                    else -> {}
                }

            }
            viewModel.orders.observe(viewLifecycleOwner){result->
                when(result){
                    is ProductsResults.Success ->{
                        Toast.makeText(requireContext(),"buy succesfully",Toast.LENGTH_LONG).show()
                    }
                    is ProductsResults.Error ->{
                        Log.d("OrdersError",result.exception)
                    }
                }
            }
        }
    }

    private fun addToCart(productItem: ProductsItem) {
        binding.btnAddToCard.setOnClickListener {
            productItem._id?.let { id -> viewModel.addToCard(id) }
            viewModel.addToCartLiveData.observe(viewLifecycleOwner){result->
                when(result){
                    is ProductsResults.Success -> {
                        Toast.makeText(requireContext(),getString(R.string.succesfully_added),Toast.LENGTH_LONG).show()
                    }
                    is ProductsResults.Error ->{
                    Log.d("ProductsResults.Error ",result.exception)
                    }
                    else -> {}
                }
            }
        }
    }



    private fun setSimilarProducts(category: String) {
        binding.rvSimilarProducts.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        similarProductsAdapter = SimilarProductsAdapter(
            this,
            this,
            SimilarProductsAdapter.DiffCallback()
        )
        lifecycleScope.launch {
            viewModel.getFilteredProducts(null, null, category, null)
                .asFlow().collect{pagingData->
                    similarProductsAdapter.submitData(pagingData)
                }
        }
        binding.rvSimilarProducts.adapter = similarProductsAdapter
    }


    private fun getProductReviews(productItem: ProductsItem?) {
        val productId =productItem?._id.toString()
        viewModel.getProductReviews(productId)
        viewModel.reviewsLiveData.observe(viewLifecycleOwner){result->
            when(result){
                is ProductsResults.Success -> {
                    binding.reviewsNumber.text=result.data.size.toString()
                }
                is ProductsResults.Error ->{
                    Log.d("ProductsResults.Error ",result.exception)
                }
                else -> {}
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun assignProductDetails(productItem: ProductsItem) {
        binding.rvImgSlider.adapter = imgSliderAdapter

        binding.rvProductImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                imgSliderAdapter.updateProductPosition(binding.rvProductImages.getCurrentPosition())
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        imgSliderAdapter.submitImgSliderData(productItem.imageUrls?.size!!)
        with(binding){
            val currentPrice=productItem.currentPrice
            val previousPrice=productItem.previousPrice
            val discount=previousPrice!!-currentPrice!!
            var discountPercent=0.0
            tvPrice.text= "US $$currentPrice"
            tvPriceBeforeDiscount.text="US $$previousPrice"
            tvDesc.text=productItem.description
            tvProductName.text=productItem.name
            if (currentPrice!=previousPrice && previousPrice!=0.0){
                discountPercent=discount.div(previousPrice).times(100)
                tvPriceBeforeDiscount.paintFlags = tvPriceBeforeDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                tvPriceBeforeDiscount.paintFlags = Paint.HINTING_OFF
            }


            setProductImagesRecycleView(productItem,discountPercent)
            getProductReviews(productItem)
            setSimilarProducts(productItem.categories.toString())
            addToCart(productItem)
            sendProductItemReview(productItem)
        }

    }

    private fun sendProductItemReview(productItem: ProductsItem) {
        binding.itemActionReviews.setOnClickListener {
            val action=ProductDetailFragmentDirections.actionProductDetailFragmentToProductReviewsFragment(productItem)
            findNavController().navigate(action)
        }
    }

    private fun setProductImagesRecycleView(productItem: ProductsItem,discountPercent: Double) {
        val layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        binding.rvProductImages.layoutManager =layoutManager
        val adapter = ProductDetailImagesAdapter(productItem,discountPercent,this,this)
        binding.rvProductImages.adapter = adapter
    }

    override fun onItemClick(productItem: ProductsItem) {
        val action=ProductDetailFragmentDirections.actionProductDetailFragmentToProductFullDetailFragment(productItem)
        findNavController().navigate(action)
    }

    override fun onSimilarItemClick(productItem: ProductsItem) {
         assignProductDetails(productItem)
    }

    override fun addToWishList(productItem: ProductsItem) {
        viewModel.addToWishList(productItem._id.toString())
        viewModel.addToWishListLiveData.observe(viewLifecycleOwner){result->
            when(result){
                is ProductsResults.Success ->{
                    Toast.makeText(requireContext(),getString(R.string.succesfully_added),Toast.LENGTH_LONG).show()
                }
                is ProductsResults.Error ->{
                    Log.d("ProductsResultError ",result.exception)
                }
                else -> {}
            }
        }
    }

}