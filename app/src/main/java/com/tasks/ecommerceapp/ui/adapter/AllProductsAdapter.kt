package com.tasks.ecommerceapp.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.databinding.ItemProductsAllListBinding
import com.tasks.ecommerceapp.databinding.ItemProductsGroupBinding
import com.tasks.ecommerceapp.utils.ProductListType

class AllProductsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var productsType = ProductListType.LIST_TYPE
    private val productItems = mutableListOf<String>()

    class ViewHolderGroup(private val binding: ItemProductsGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() = with(binding) {
            tvName.setText("yunus")
            tvProductDiscount.text = "-20%"
            tvPriceBeforeDiscount.paintFlags =
                tvPriceBeforeDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

    }

    class ViewHolderList(private val binding: ItemProductsAllListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() = with(binding) {
            tvName.setText("second yunus")
            tvPriceBeforeDiscount.paintFlags =
                tvPriceBeforeDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tvProductDiscount.text = "-10%"
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
                (holder as ViewHolderGroup).bind()
            }

            ProductListType.LIST_TYPE.type -> {
                (holder as ViewHolderList).bind()
            }
        }
    }

    override fun getItemCount(): Int {
        return productItems.size
    }


    fun submitList(productsList: List<String>) {
        productItems.clear()
        productItems.addAll(productsList)
        notifyDataSetChanged()
    }

    fun setProductType(productType: ProductListType) {
        this.productsType = productType
        notifyDataSetChanged()
    }


}