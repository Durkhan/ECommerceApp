package com.tasks.ecommerceapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.databinding.ItemImageSliderBinding

class ImgSliderAdapter : RecyclerView.Adapter<ImgSliderAdapter.ViewHolder>() {
    private val productImgSliderData = mutableListOf<Int>()
    private var currentProductPosition = 0

    inner class ViewHolder(private val binding: ItemImageSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.ivSlidePointer.isEnabled = absoluteAdapterPosition == currentProductPosition
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemImageSliderBinding.inflate(
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
        return productImgSliderData.size
    }

    fun submitImgSliderData(data: Int) {
        productImgSliderData.clear()
        for (i in 1..data)
            productImgSliderData.add(i)
    }

    fun updateProductPosition(position: Int) {
        currentProductPosition = position
        notifyDataSetChanged()
    }
}