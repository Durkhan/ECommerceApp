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
import com.tasks.ecommerceapp.databinding.ItemProductsAllListBinding
import com.tasks.ecommerceapp.databinding.ItemProductsGroupBinding
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.common.ProductListType
import com.tasks.ecommerceapp.common.listener.OnItemClickListener


class AllProductsAdapter(
    private var context: Context?,
    private var onItemClickListener: OnItemClickListener,
    diffCallback: DiffUtil.ItemCallback<ProductsItem>
) : PagingDataAdapter<ProductsItem, RecyclerView.ViewHolder>(diffCallback) {

    private var productsType = ProductListType.LIST_TYPE
    class ViewHolderGroup(private val binding: ItemProductsGroupBinding) :
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
                tvProductDiscount.text="-"+String.format("%.0f", discountPercent)+"%"
                tvPriceBeforeDiscount.paintFlags = tvPriceBeforeDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            else{
                tvProductDiscount.text="0%"
                tvPriceBeforeDiscount.paintFlags = Paint.HINTING_OFF
            }




        }
        }

    class ViewHolderList(private val binding: ItemProductsAllListBinding) :
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

            if (currentPrice!=previousPrice){
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
        val item=getItem(position)
       holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(item!!)
        }
        when (holder.itemViewType) {
            ProductListType.GROUP_TYPE.type -> {
                item?.let { (holder as ViewHolderGroup).bind(it,context) }
            }

            ProductListType.LIST_TYPE.type -> {
                item?.let { (holder as ViewHolderList).bind(it,context) }

            }
        }
    }



    fun setProductType(productType: ProductListType) {
        this.productsType = productType
        notifyDataSetChanged()
    }
    class DiffCallback : DiffUtil.ItemCallback<ProductsItem>() {
        override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
            return oldItem._id.toString() == newItem._id.toString()
        }

        override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
            return oldItem == newItem
        }
    }

}