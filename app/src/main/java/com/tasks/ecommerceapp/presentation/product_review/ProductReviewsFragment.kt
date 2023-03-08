package com.tasks.ecommerceapp.presentation.product_review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tasks.ecommerceapp.data.model.customer.product.ReviewsForProductResponse
import com.tasks.ecommerceapp.databinding.FragmentProductReviewsBinding
import com.tasks.ecommerceapp.presentation.adapter.ProductReviewsAdapter
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment

class ProductReviewsFragment : BaseViewBindingFragment<FragmentProductReviewsBinding>() {

    private var productReviewsAdapter = ProductReviewsAdapter()
    private val viewModel: ProductsViewModel by viewModels()

    override fun getViewBinding() = FragmentProductReviewsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvComments.adapter = productReviewsAdapter
        initReviewsObserver()
        viewModel.getReviewsForProduct("1234")
    }

    private fun initReviewsObserver() {
        viewModel.productReviewsLiveData.observe(viewLifecycleOwner) {
            setProductReviewsData(it)
        }
    }

    private fun setProductReviewsData(reviews: ReviewsForProductResponse) = with(binding) {
        tvName.text = reviews.product?.name
        tvPrice.text = reviews.product?.currentPrice?.toString()
        tvPreviousPrice.text = reviews.product?.previousPrice?.toString()

        productReviewsAdapter.submitList(listOf(reviews))
    }


}