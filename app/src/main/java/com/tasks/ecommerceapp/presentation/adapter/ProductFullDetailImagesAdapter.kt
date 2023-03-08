package com.tasks.ecommerceapp.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.databinding.ItemProductDetailBinding
import com.tasks.ecommerceapp.databinding.ItemProductDetailFullBinding
import java.util.*

class ProductFullDetailImagesAdapter (private var context: Context, private var imageUrls: List<String>, private var discountPercent: Double) : PagerAdapter() {

    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return  view=== `object` as ConstraintLayout
    }

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater=LayoutInflater.from(context)
        val binding=ItemProductDetailFullBinding.inflate(inflater,container,false)

        Glide.with(context).load(imageUrls[position])
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(android.R.drawable.stat_notify_error)
            .into(binding.ivProduct);
        binding.tvProductDiscount.text="-"+String.format("%.0f", discountPercent)+"%"

        Objects.requireNonNull(container).addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

}
