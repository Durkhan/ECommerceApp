package com.tasks.ecommerceapp.presentation.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tasks.ecommerceapp.databinding.FragmentOrdersBinding
import com.tasks.ecommerceapp.presentation.adapter.OrderSelectionItem
import com.tasks.ecommerceapp.presentation.adapter.OrdersAdapter
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : BaseViewBindingFragment<FragmentOrdersBinding>() {

    private val ordersAdapter = OrdersAdapter()
    private val ordersViewModel: OrdersViewModel by viewModels()

    override fun getViewBinding() = FragmentOrdersBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvAdapter()
        initOrdersObserver()
        ordersViewModel.getAllOrders()
    }

    private fun initRvAdapter() {
        binding.rvOrders.adapter = ordersAdapter

        ordersAdapter.submitSelectionData(
            listOf(
                OrderSelectionItem(status = "Ongoing"),
                OrderSelectionItem(status = "Completed"),
                OrderSelectionItem(status = "Cancelled")
            )
        )
    }

    private fun initOrdersObserver() {
        ordersViewModel.ordersLiveData.observe(viewLifecycleOwner) {
            ordersAdapter.submitOrdersData(it)
        }
    }
}