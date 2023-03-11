package com.tasks.ecommerceapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.data.model.customer.orders.GetAllOrdersResponse
import com.tasks.ecommerceapp.databinding.ItemOrdersBinding

class OrdersAdapter : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    private val orderedProductsList = mutableListOf<GetAllOrdersResponse>()
    private val selectionItemList = mutableListOf<OrderSelectionItem>()


    inner class ViewHolder(val binding: ItemOrdersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                selectItem(absoluteAdapterPosition)
            }
        }

        fun bind(model: OrderSelectionItem) = with(binding) {
            ibSelection.isSelected = model.selected
            rvProducts.isVisible = model.selected
            lnEmptyDetail.isVisible = model.selected
            tvStatus.text = model.status

            val productsAdapter = OrderProductAdapter()
            rvProducts.adapter = productsAdapter

            orderedProductsList.forEach { productOrder ->
                if (absoluteAdapterPosition == 1 && productOrder.status == "shipped" || productOrder.status == "completed") {
                    productsAdapter.status = productOrder.status ?: ""
                    lnEmptyDetail.isVisible = productOrder.products.isEmpty()
                    productsAdapter.submitData(productOrder.products)
                } else if (absoluteAdapterPosition == 2 && productOrder.status == "cancelled") {
                    productsAdapter.status = productOrder.status ?: ""
                    lnEmptyDetail.isVisible = productOrder.products.isEmpty()
                    productsAdapter.submitData(productOrder.products)
                } else if (absoluteAdapterPosition == 0 && productOrder.status == "In delivery") {
                    productsAdapter.status = productOrder.status ?: ""
                    lnEmptyDetail.isVisible = productOrder.products.isEmpty()
                    productsAdapter.submitData(productOrder.products)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersAdapter.ViewHolder {
        val binding = ItemOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersAdapter.ViewHolder, position: Int) {
        holder.bind(selectionItemList[position])
    }

    override fun getItemCount() = selectionItemList.size

    fun submitSelectionData(selectionItemData: List<OrderSelectionItem>) {
        this.selectionItemList.clear()
        this.selectionItemList.addAll(selectionItemData)
        notifyDataSetChanged()
    }

    fun submitOrdersData(ordersData: List<GetAllOrdersResponse>) {
        this.orderedProductsList.clear()
        this.orderedProductsList.addAll(ordersData)
        notifyDataSetChanged()
    }

    fun selectItem(position: Int) {
        val newItems = selectionItemList.mapIndexed { index, orderSelectionItem ->
            orderSelectionItem.copy(selected = (index == position) != orderSelectionItem.selected)
        }
        submitSelectionData(newItems)
    }

}