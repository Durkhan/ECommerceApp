package com.tasks.ecommerceapp.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.databinding.ItemAddProductBinding

class ProductImagesAdapter : RecyclerView.Adapter<ProductImagesAdapter.ViewHolder>() {

    private var productImagesList = mutableListOf<Uri>()

    inner class ViewHolder(val binding: ItemAddProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Uri) {
            Glide.with(binding.ivProduct).load(data).into(binding.ivProduct)

            binding.ibDelete.setOnClickListener {
                deleteProduct(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAddProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productImagesList[position])
    }

    override fun getItemCount(): Int {
        return productImagesList.size
    }

    fun updateList(productImageUri: Uri) {
        this.productImagesList.add(productImageUri)
        notifyDataSetChanged()
    }

    fun deleteProduct(position: Int) {
        productImagesList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getProductImages() = productImagesList
}