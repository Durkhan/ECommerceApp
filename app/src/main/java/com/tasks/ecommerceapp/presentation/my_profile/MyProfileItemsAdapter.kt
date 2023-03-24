package com.tasks.ecommerceapp.presentation.my_profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.listener.PasswordChangeListener
import com.tasks.ecommerceapp.databinding.MyProfileItemsBinding

class MyProfileItemsAdapter(
    private val texts:List<String>,
    private val icons :List<Int>,
    private var passwordChangeListener: PasswordChangeListener
    ):RecyclerView.Adapter<MyProfileItemsAdapter.ItemsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyProfileItemsAdapter.ItemsViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=MyProfileItemsBinding.inflate(inflater,parent,false)
        return ItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyProfileItemsAdapter.ItemsViewHolder, position: Int) {
          with(holder.binding){
              name.text=texts[position]
              icon.setImageResource(icons[position])

              if (position==(icons.size-1)){
                  name.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.carrot))
                  icon.imageTintList=ContextCompat.getColorStateList(
                      holder.itemView.context,
                      R.color.carrot
                  )
                  holder.itemView.setOnClickListener {
                     passwordChangeListener.onClick()
                  }
              }
          }
    }

    override fun getItemCount(): Int {
        return icons.size
    }
    class ItemsViewHolder(val binding:MyProfileItemsBinding):RecyclerView.ViewHolder(binding.root){}
}