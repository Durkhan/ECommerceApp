package com.tasks.ecommerceapp.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.databinding.ItemProductsBinding
import com.tasks.ecommerceapp.data.model.customer.product.ProductResponse


class ProductsinHomeAdapter(private var data: List<ProductResponse>) : RecyclerView.Adapter<ProductsinHomeAdapter.ProductsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsinHomeAdapter.ProductsViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=ItemProductsBinding.inflate(inflater,parent,false)
        return ProductsViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductsinHomeAdapter.ProductsViewHolder, position: Int) {
        with(holder.binding) {
            Glide.with(holder.itemView.context)
                .load(data[position].imageUrls?.get(0))
                .into(ivProduct)
            tvName.text = data[position].name
            tvDesc.text = data[position].description
            tvPrice.text = "US $" + data[position].currentPrice.toString()
            val currentPrice = data[position].currentPrice
            val previousPrice = data[position].previousPrice
            val discount = previousPrice!! - currentPrice!!
            tvPriceBeforeDiscount.text = "US $$previousPrice"
            if (currentPrice != previousPrice) {
                val discountPercent = discount.div(previousPrice).times(100)
                tvProductDiscount.text = "-" + String.format("%.0f", discountPercent) + "%"
                tvPriceBeforeDiscount.paintFlags =
                    tvPriceBeforeDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            } else {
                tvProductDiscount.text = "0%"
                tvPriceBeforeDiscount.paintFlags = Paint.HINTING_OFF
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ProductsViewHolder(val binding: ItemProductsBinding):RecyclerView.ViewHolder(binding.root){}
}