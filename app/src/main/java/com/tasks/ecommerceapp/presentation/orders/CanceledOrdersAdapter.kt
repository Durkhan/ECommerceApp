package com.tasks.ecommerceapp.presentation.orders


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.data.model.customer.orders.Order
import com.tasks.ecommerceapp.databinding.ItemOrdersBinding

class CanceledOrdersAdapter(
    private val orders: List<Order>,
) : RecyclerView.Adapter<CanceledOrdersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            lnEmptyDetail.isVisible = orders.isEmpty()
            constraint.isVisible=orders.isNotEmpty()
            statusImage.setImageResource(R.drawable.canceled)
            statusText.text = holder.itemView.context.getString(R.string.orders_canceled_desc)


        if (orders.isNotEmpty()){
            val product = orders[position].products?.get(0)?.product
            tvStatus.text = holder.itemView.context.getString(R.string.canceled)
            tvName.text = product?.name
            btnStatus.text = holder.itemView.context.getString(R.string.buy_again)
            tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.order_cancel))
            tvStatus.setBackgroundResource(R.drawable.canceled_order)
            tvPrice.text = "US $${product?.currentPrice}"
            Glide.with(holder.itemView.context)
              .load(product?.imageUrls?.get(0))
              .into(ivProduct)
        }

}


    }

    override fun getItemCount() = if(orders.isEmpty()) 1 else orders.size

    class ViewHolder(val binding: ItemOrdersBinding) : RecyclerView.ViewHolder(binding.root)
}