package com.tasks.ecommerceapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.databinding.ItemCardCheckoutBinding
import com.tasks.ecommerceapp.common.ProductCheckOutData

class ProductCheckoutAdapter : RecyclerView.Adapter<ProductCheckoutAdapter.ViewHolder>() {
    private val products = mutableListOf<ProductCheckOutData>()
    private var currentProductPosition = 0

    var productDeleteEvent: ((Boolean) -> Unit?)? = null
    var productsTotalPriceEvent: ((Double) -> Unit?)? = null

    inner class ViewHolder(private val binding: ItemCardCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {

            chkSelect.isChecked = false

            tvName.text = products[adapterPosition].name

            ibRemove.setOnClickListener {
                if (tvProductCount.text.toString() == "1") {
                    return@setOnClickListener
                }
                val productCount = (tvProductCount.text.toString().toInt() - 1)
                tvProductCount.text = productCount.toString()
                products[adapterPosition].count = tvProductCount.text.toString().toInt()

                if (checkAllProductsSelection()) {
                    val totalPrice = calculateTotalPrice()
                    productsTotalPriceEvent?.invoke(totalPrice)
                }

            }

            ibAdd.setOnClickListener {
                val productCount = (tvProductCount.text.toString().toInt() + 1)
                tvProductCount.text = productCount.toString()
                products[adapterPosition].count = tvProductCount.text.toString().toInt()

                if (checkAllProductsSelection()) {
                    val totalPrice = calculateTotalPrice()
                    productsTotalPriceEvent?.invoke(totalPrice)
                }
            }

            chkSelect.setOnCheckedChangeListener { _, selected ->
                products[adapterPosition].selected = selected
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
        holder.bind()
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun submitData(data: List<ProductCheckOutData>) {
        products.clear()
        products.addAll(data)
        notifyDataSetChanged()
    }

    fun removeSelectedProducts() {
        val selectedProducts = mutableListOf<ProductCheckOutData>()

        products.forEach { item ->
            if (item.selected) {
                selectedProducts.add(item)
            }
        }


        selectedProducts.forEach {
            val index = products.indexOf(it)
            products.remove(it)
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, products.size)
            productsTotalPriceEvent?.invoke(0.0)
        }
    }

    fun calculateTotalPrice(): Double {
        var productTotalPrice = 0.0
        products.forEach {
            if (it.selected) {
                productTotalPrice += (it.price) * it.count
            }
        }
        return productTotalPrice
    }

}