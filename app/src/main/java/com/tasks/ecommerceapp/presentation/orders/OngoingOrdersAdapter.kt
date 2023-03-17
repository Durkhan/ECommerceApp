package com.tasks.ecommerceapp.presentation.orders

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.listener.CompleteOrderListener
import com.tasks.ecommerceapp.data.model.customer.orders.Order
import com.tasks.ecommerceapp.databinding.ItemOrdersBinding

class OngoingOrdersAdapter(private val orders:List<Order>,private val completeOrderListener: CompleteOrderListener) : RecyclerView.Adapter<OngoingOrdersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OngoingOrdersAdapter.ViewHolder {
        val binding = ItemOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OngoingOrdersAdapter.ViewHolder, position: Int) {
        with(holder.binding) {
                lnEmptyDetail.isVisible = orders.isEmpty()
                statusImage.setImageResource(R.drawable.ongoing)
                statusText.text = holder.itemView.context.getString(R.string.orders_ongoing_desc)

            if (orders.isNotEmpty()) {
                val product = orders[position].products?.get(0)?.product
                tvStatus.text = holder.itemView.context.getString(R.string.in_delivery)
                tvName.text = product?.name
                btnStatus.text = holder.itemView.context.getString(R.string.completed)
                tvPrice.text = "US $${product?.currentPrice}"
                Glide.with(holder.itemView.context)
                    .load(product?.imageUrls?.get(0))
                    .into(ivProduct)
                btnStatus.setOnClickListener { completeOrderListener.clickCheckOut(orders[position]) }
            }
        }

    }

    override fun getItemCount() = if(orders.isEmpty()) 1 else orders.size

    class ViewHolder(val binding: ItemOrdersBinding) : RecyclerView.ViewHolder(binding.root) {}

}