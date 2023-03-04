package com.tasks.ecommerceapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.customer.catalog.CatalogResponse
import com.tasks.ecommerceapp.databinding.ItemShopCategoiresBinding

class CategoryAdapter(private var catagories:List<CatalogResponse>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):CategoryAdapter.CategoryViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding=ItemShopCategoiresBinding.inflate(inflater,parent,false)
        return CategoryViewHolder(binding)
    }



    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(catagories[position].imgUrl)
            .into(holder.binding.ivCategory)

        holder.binding.tvCategoryName.text=catagories[position].name
    }

    override fun getItemCount(): Int {
           return catagories.size
    }

    class CategoryViewHolder(val binding: ItemShopCategoiresBinding):RecyclerView.ViewHolder(binding.root) {}
}