package com.tasks.ecommerceapp.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.common.listener.SimilarItemClickListener
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.databinding.ItemProductsBinding

class SimilarProductsAdapter (
    private var similarItemClickListener: SimilarItemClickListener,
    diffCallback: DiffUtil.ItemCallback<ProductsItem>,
) : PagingDataAdapter<ProductsItem, RecyclerView.ViewHolder>(diffCallback) {

    class ViewHolderGroup(private val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(productItem: ProductsItem, context: Context?) = with(binding) {
            Glide.with(context!!)
                .load(productItem.imageUrls?.get(0)!!)
                .into(binding.ivProduct)
            tvName.text=productItem.name
            tvDesc.text=productItem.description
            val currentPrice=productItem.currentPrice
            val previousPrice=productItem.previousPrice
            val discount=previousPrice!!-currentPrice!!
            tvPrice.text= "US $$currentPrice"
            tvPriceBeforeDiscount.text="US $$previousPrice"

            if (currentPrice!=previousPrice && previousPrice!=0.0){
                val discountPercent=discount.div(previousPrice).times(100)
                tvProductDiscount.text="-"+ String.format("%.0f", discountPercent)+"%"
                tvPriceBeforeDiscount.paintFlags = tvPriceBeforeDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            else{
                tvProductDiscount.text="0%"
                tvPriceBeforeDiscount.paintFlags = Paint.HINTING_OFF
            }




        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ProductsItem>() {
        override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item=getItem(position)
        item?.let { (holder as ViewHolderGroup).bind(it,holder.itemView.context) }
        holder.itemView.setOnClickListener {
            item?.let { it1 -> similarItemClickListener.onSimilarItemClick(it1) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderGroup(
            ItemProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


}

