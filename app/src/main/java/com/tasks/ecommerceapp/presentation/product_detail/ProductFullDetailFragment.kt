package com.tasks.ecommerceapp.presentation.product_detail

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.EmptyViewPageListener
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.common.listener.AddToWishListListener
import com.tasks.ecommerceapp.common.listener.SimilarItemClickListener
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.databinding.FragmentProductFullDetailBinding
import com.tasks.ecommerceapp.domain.usecases.product.GetFilteringProductsUseCase
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment
import com.tasks.ecommerceapp.presentation.adapter.ImgSliderAdapter
import com.tasks.ecommerceapp.presentation.adapter.ProductFullDetailImagesAdapter
import com.tasks.ecommerceapp.presentation.adapter.SimilarProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductFullDetailFragment : BaseViewBindingFragment<FragmentProductFullDetailBinding>(),
    SimilarItemClickListener,AddToWishListListener {

    private val imgSliderAdapter = ImgSliderAdapter()
    private lateinit var similarProductsAdapter: SimilarProductsAdapter
    private val args:ProductFullDetailFragmentArgs by navArgs()
    private val viewModel: ProductFullDetailViewModel by viewModels()

    @Inject
    lateinit var getFilteringProductsUseCase: GetFilteringProductsUseCase

    override fun getViewBinding() = FragmentProductFullDetailBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvImgSlider.adapter = imgSliderAdapter

        binding.rvProductImages.addOnPageChangeListener(object :EmptyViewPageListener(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                imgSliderAdapter.updateProductPosition(binding.rvProductImages.currentItem)
            }

        })


        val productItem=args.productsItem
        imgSliderAdapter.submitImgSliderData(productItem?.imageUrls?.size!!)
        assignProductDetails(productItem)

        binding.ibReturnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
    }
    private fun addToCart(productItem: ProductsItem) {
        binding.btnAddToCard.setOnClickListener {
            productItem._id?.let { id -> viewModel.addToCard(id) }
            viewModel.addToCartLiveData.observe(viewLifecycleOwner){result->
                when(result){
                    is ProductsResults.Success -> {
                        Toast.makeText(requireContext(),getString(R.string.succesfully_added),
                            Toast.LENGTH_LONG).show()
                    }
                    is ProductsResults.Error ->{
                        Log.d("ProductsResults.Error ",result.exception)
                    }
                    else -> {}
                }
            }
        }
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
            val action=ProductFullDetailFragmentDirections.actionProductFullDetailFragmentToProductReviewsFragment(productItem)
            findNavController().navigate(action)
        }
    }


    private fun setProductImagesRecycleView(productItem: ProductsItem,discountPercent: Double) {
        val adapter = ProductFullDetailImagesAdapter(requireContext(),productItem,discountPercent,this)
        binding.rvProductImages.adapter = adapter
    }


    private fun setSimilarProducts(category: String) {
        binding.rvSimilarProducts.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        similarProductsAdapter = SimilarProductsAdapter(
            this,
            this,
            SimilarProductsAdapter.DiffCallback(),
        )
        lifecycleScope.launch {
           getFilteringProductsUseCase(null, null, category, null)
                .collect{pagingData->
                    similarProductsAdapter.submitData(pagingData)
                }
        }
        binding.rvSimilarProducts.adapter = similarProductsAdapter
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
                    Log.d("ProductsResultsError",result.exception)
                }
                else -> {}
            }
        }
    }

}
