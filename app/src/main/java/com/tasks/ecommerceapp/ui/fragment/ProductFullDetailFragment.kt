package com.tasks.ecommerceapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.base.BaseViewBindingFragment
import com.tasks.ecommerceapp.databinding.FragmentProductFullDetailBinding
import com.tasks.ecommerceapp.extensions.getCurrentPosition
import com.tasks.ecommerceapp.ui.adapter.ImgSliderAdapter
import com.tasks.ecommerceapp.ui.adapter.ProductDetailAdapter
import com.tasks.ecommerceapp.ui.adapter.ProductFullDetailAdapter

class ProductFullDetailFragment : BaseViewBindingFragment<FragmentProductFullDetailBinding>() {
    private val productDetailAdapter = ProductFullDetailAdapter()
    private val imgSliderAdapter = ImgSliderAdapter()


    override fun getViewBinding() = FragmentProductFullDetailBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvProductImages.adapter = productDetailAdapter
        binding.rvImgSlider.adapter = imgSliderAdapter

        binding.rvProductImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                imgSliderAdapter.updateProductPosition(binding.rvProductImages.getCurrentPosition())
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        imgSliderAdapter.submitImgSliderData(listOf(1, 2, 3, 4, 5))
        productDetailAdapter.submitProductImages(listOf(1, 2, 3, 4, 5))
    }
}