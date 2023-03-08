package com.tasks.ecommerceapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.data.model.customer.review.ProductReviewResponse
import com.tasks.ecommerceapp.databinding.ItemProductReviewsBinding

class ProductReviewsAdapter : RecyclerView.Adapter<ProductReviewsAdapter.ViewHolder>() {

    private val reviewsList = mutableListOf<ProductReviewResponse>()

    class ViewHolder(private val binding: ItemProductReviewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductReviewResponse) = with(binding) {
            tvComment.text = product.content
            tvCommentDate.text = product.customer?.date
            tvCustomerName.text = product.customer?.firstName

            if (!product.product?.imageUrls.isNullOrEmpty()) {
                lnProductImages.removeAllViews()
                product.product?.imageUrls?.forEach {
                    val imgView = ImageView(binding.root.context)
                    imgView.layoutParams = ViewGroup.LayoutParams(80, 80)
                    imgView.updatePadding(left = 10)
                    lnProductImages.addView(imgView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemProductReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewsList[position])
    }

    override fun getItemCount() = reviewsList.size

    fun submitList(reviewsList: List<ProductReviewResponse>) {
        this.reviewsList.clear()
        this.reviewsList.addAll(reviewsList)
        notifyDataSetChanged()
    }

}