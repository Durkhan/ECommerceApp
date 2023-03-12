package com.tasks.ecommerceapp.presentation.product_review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.data.model.customer.review.ProductReviewResponse
import com.tasks.ecommerceapp.databinding.FragmentProductReviewsBinding
import com.tasks.ecommerceapp.presentation.adapter.ProductReviewsAdapter
import com.tasks.ecommerceapp.presentation.allproducts.AllProductViewModel
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductReviewsFragment : BaseViewBindingFragment<FragmentProductReviewsBinding>() {

    private var productReviewsAdapter = ProductReviewsAdapter()
    private val viewModel: AllProductViewModel by viewModels()
    private val args:ProductReviewsFragmentArgs by navArgs()
    override fun getViewBinding() = FragmentProductReviewsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvComments.adapter = productReviewsAdapter
        initReviewsObserver()

    }

    private fun initReviewsObserver() {
        viewModel.getProductReviews(args.productsItem?._id.toString())
        viewModel.reviewsLiveData.observe(viewLifecycleOwner) {result->
            when(result){
                is ProductsResults.Success-> {
                    setProductReviewsData(result.data)
                }
                else -> {}
            }
        }

        }
    private fun setProductReviewsData(reviews: List<ProductReviewResponse>) = with(binding) {
        productReviewsAdapter.submitList(reviews)
    }
    }



