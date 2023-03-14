package com.tasks.ecommerceapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.common.listener.DeleteProductFromWishListListener
import com.tasks.ecommerceapp.data.model.customer.product.ProductsItem
import com.tasks.ecommerceapp.databinding.ItemWishlistBinding
import com.tasks.ecommerceapp.presentation.wishlist.WishListFragment


class WishListAdapter(
    private val response: List<ProductsItem>,
    private val deleteProductFromWishListListener: DeleteProductFromWishListListener
    ) : RecyclerView.Adapter<WishListAdapter.ItemsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WishListAdapter.ItemsViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemWishlistBinding.inflate(inflater,parent,false)
        return ItemsViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WishListAdapter.ItemsViewHolder, position: Int) {
        with(holder.binding){
            Glide.with(holder.itemView.context)
                .load(response[position].imageUrls?.get(0))
                .into(ivProduct)
            tvName.text=response[position].name
            tvPrice.text="US $${response[position].currentPrice}"
            ibHeart.setOnClickListener {
                deleteProductFromWishListListener.deleteProduct(response[position])
            }
        }

    }

    override fun getItemCount(): Int {
        return response.size
    }

    class ItemsViewHolder(val binding: ItemWishlistBinding): RecyclerView.ViewHolder(binding.root){}
}