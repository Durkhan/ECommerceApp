package com.tasks.ecommerceapp.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.databinding.ItemProductsAllListBinding
import com.tasks.ecommerceapp.databinding.ItemProductsGroupBinding
import com.tasks.ecommerceapp.data.model.customer.product.SearchProductResponse
import com.tasks.ecommerceapp.common.ProductListType


class SearchedProductsAdapter(private val data: List<SearchProductResponse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var productsType = ProductListType.LIST_TYPE

    class ViewHolderGroup(private val binding: ItemProductsGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: List<SearchProductResponse>, context: Context) = with(binding) {
            Glide.with(context)
                .load(data[absoluteAdapterPosition].imageUrls?.get(0))
                .into(binding.ivProduct)
            tvName.text=data[absoluteAdapterPosition].name
            tvDesc.text=data[absoluteAdapterPosition].description
            tvPrice.text="US $"+data[absoluteAdapterPosition].currentPrice.toString()
            val currentPrice=data[absoluteAdapterPosition].currentPrice
            val previousPrice=data[absoluteAdapterPosition].previousPrice

            if(previousPrice!=null && currentPrice!=null){
                tvPriceBeforeDiscount.text= "US $$previousPrice"
                val discount=previousPrice-currentPrice
                if (currentPrice!=previousPrice && previousPrice!=0.0){
                    val discountPercent=discount.div(previousPrice).times(100)
                    tvProductDiscount.text="-"+String.format("%.0f", discountPercent)+"%"
                    tvPriceBeforeDiscount.paintFlags = tvPriceBeforeDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                }
                else{
                    tvProductDiscount.text="0%"
                    tvPriceBeforeDiscount.paintFlags = Paint.HINTING_OFF

                }
            }


        }

    }

    class ViewHolderList(private val binding: ItemProductsAllListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: List<SearchProductResponse>, context: Context) = with(binding) {


            Glide.with(context)
                .load(data[absoluteAdapterPosition].imageUrls?.get(0))
                .into(binding.ivProduct)
            tvName.text=data[absoluteAdapterPosition].name
            tvDesc.text=data[absoluteAdapterPosition].description
            tvPrice.text="US $"+data[absoluteAdapterPosition].currentPrice.toString()
            val currentPrice=data[absoluteAdapterPosition].currentPrice
            val previousPrice=data[absoluteAdapterPosition].previousPrice

            if(previousPrice!=null && currentPrice!=null){
                tvPriceBeforeDiscount.text= "US $$previousPrice"
                val discount=previousPrice-currentPrice
                if (currentPrice!=previousPrice && previousPrice!=0.0){
                    val discountPercent=discount.div(previousPrice).times(100)
                    tvProductDiscount.text="-"+String.format("%.0f", discountPercent)+"%"
                    tvPriceBeforeDiscount.paintFlags = tvPriceBeforeDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                }
                else{
                    tvProductDiscount.text="0%"
                    tvPriceBeforeDiscount.paintFlags = Paint.HINTING_OFF

                }
            }


        }

    }

    override fun getItemViewType(position: Int): Int {
        return productsType.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ProductListType.LIST_TYPE.type -> {
                return ViewHolderList(
                    ItemProductsAllListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ProductListType.GROUP_TYPE.type -> {
                return ViewHolderGroup(
                    ItemProductsGroupBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        return ViewHolderList(
            ItemProductsAllListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ProductListType.GROUP_TYPE.type -> {
                (holder as ViewHolderGroup).bind(data,holder.itemView.context)
            }

            ProductListType.LIST_TYPE.type -> {
                (holder as ViewHolderList).bind(data,holder.itemView.context)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }



    fun setProductType(productType: ProductListType) {
        this.productsType = productType
        notifyDataSetChanged()
    }


}

