package com.tasks.ecommerceapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.databinding.ItemProductDetailBinding

class ProductDetailAdapter : RecyclerView.Adapter<ProductDetailAdapter.ViewHolder>() {

    private val productImages = mutableListOf<Int>()

    inner class ViewHolder(private val binding: ItemProductDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }


    override fun getItemCount(): Int {
        return productImages.size
    }

    fun submitProductImages(data: List<Int>) {
        productImages.clear()
        productImages.addAll(data)
        notifyDataSetChanged()
    }

}