package com.tasks.ecommerceapp.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.data.model.customer.review.Product
import com.tasks.ecommerceapp.databinding.ItemOrderProductDetailBinding

class OrderProductAdapter : RecyclerView.Adapter<OrderProductAdapter.ViewHolder>() {

    private val products = mutableListOf<Product>()
    var status: String = ""

    inner class ViewHolder(val binding: ItemOrderProductDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Product) = with(binding) {
            tvStatus.text = status
            tvName.text = model.name
            tvPrice.text = model.currentPrice.toString()

            //ivProduct.setImageURI(Uri.parse(model.imageUrls[0]))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderProductDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    fun submitData(productList: List<Product>) {
        products.clear()
        products.addAll(productList)
        notifyDataSetChanged()
    }

}