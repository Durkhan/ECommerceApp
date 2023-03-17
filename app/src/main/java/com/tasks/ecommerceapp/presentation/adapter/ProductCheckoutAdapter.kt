package com.tasks.ecommerceapp.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.databinding.ItemCardCheckoutBinding
import com.tasks.ecommerceapp.common.ProductCheckOutData

class ProductCheckoutAdapter() : RecyclerView.Adapter<ProductCheckoutAdapter.ViewHolder>() {
    private val products = mutableListOf<ProductCheckOutData>()

    var productDeleteEvent: ((Boolean) -> Unit?)? = null
    var productsTotalPriceEvent: ((Double) -> Unit?)? = null

    inner class ViewHolder(private val binding: ItemCardCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(context: Context) = with(binding) {
            val product=products[absoluteAdapterPosition].product.product
            chkSelect.isChecked = false

            tvName.text = product?.name
            tvPrice.text="US $${product?.currentPrice}"

            Glide.with(context)
                .load(product?.imageUrls?.get(0))
                .into(ivProduct)
            ibRemove.setOnClickListener {
                if (tvProductCount.text.toString() == "1") {
                    return@setOnClickListener
                }
                val productCount = (tvProductCount.text.toString().toInt() - 1)
                tvProductCount.text = productCount.toString()
                products[absoluteAdapterPosition].count = tvProductCount.text.toString().toInt()

                if (checkAllProductsSelection()) {
                    val totalPrice = calculateTotalPrice()
                    productsTotalPriceEvent?.invoke(totalPrice)
                }

            }

            ibAdd.setOnClickListener {
                val productCount = (tvProductCount.text.toString().toInt() + 1)
                tvProductCount.text = productCount.toString()
                products[absoluteAdapterPosition].count = tvProductCount.text.toString().toInt()

                if (checkAllProductsSelection()) {
                    val totalPrice = calculateTotalPrice()
                    productsTotalPriceEvent?.invoke(totalPrice)
                }
            }

            chkSelect.setOnCheckedChangeListener { _, selected ->
                products[absoluteAdapterPosition].selected = selected
                productDeleteEvent?.invoke(checkAllProductsSelection())

                val totalPrice = calculateTotalPrice()
                productsTotalPriceEvent?.invoke(totalPrice)
            }
        }
    }

    private fun checkAllProductsSelection(): Boolean {
        var selected = false
        products.forEach {
            if (it.selected) {
                selected = true
                return@forEach
            }
        }
        return selected
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCardCheckoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun submitData(data: List<ProductCheckOutData>) {
        products.clear()
        products.addAll(data)
        notifyDataSetChanged()
    }

    fun selectedProducts():List<ProductCheckOutData> {
        val selectedProducts = mutableListOf<ProductCheckOutData>()

        products.forEach { item ->
            if (item.selected) {
                selectedProducts.add(item)
            }
        }
        return selectedProducts
    }

    fun calculateTotalPrice(): Double {
        var productTotalPrice = 0.0
        products.forEach {
            if (it.selected) {
                productTotalPrice += (it.product.product?.currentPrice)?.times(it.count) ?:0.0
            }
        }
        return productTotalPrice
    }

}