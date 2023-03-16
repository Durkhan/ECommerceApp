package com.tasks.ecommerceapp.presentation.adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.common.listener.AddToWishListListener
import com.tasks.ecommerceapp.common.listener.OnItemClickListener
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.databinding.ItemProductDetailBinding


class ProductDetailImagesAdapter(
    private var productsItem: ProductsItem,
    private var discountPercent:Double,
    private var onItemClickListener: OnItemClickListener,
    private var addToWishListListener: AddToWishListListener
    ) : RecyclerView.Adapter<ProductDetailImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(productsItem.imageUrls?.get(position))
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.binding.ivProduct);
        holder.binding.tvProductDiscount.text="-"+String.format("%.0f", discountPercent)+"%"

        holder.binding.ivProduct.setOnClickListener {
            onItemClickListener.onItemClick(productsItem)
        }
        holder.binding.ibHeart.setOnClickListener {
            addToWishListListener.addToWishList(productsItem)
        }
    }

    override fun getItemCount(): Int {
        return productsItem.imageUrls?.size ?:0
    }

    class ViewHolder(var binding: ItemProductDetailBinding) : RecyclerView.ViewHolder(binding.root) {}
}