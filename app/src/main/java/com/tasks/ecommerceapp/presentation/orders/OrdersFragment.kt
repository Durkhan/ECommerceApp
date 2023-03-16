package com.tasks.ecommerceapp.presentation.orders

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentOrdersBinding
import com.tasks.ecommerceapp.common.OrderSelectionItem
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
//        initOrdersObserver()
//        ordersViewModel.getAllOrders()

        returnBack()
    }

    private fun returnBack() {
        binding.header.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }

    }

    private fun initRvAdapter() {
        binding.rvOrders.adapter = ordersAdapter

        ordersAdapter.submitSelectionData(
            listOf(
                OrderSelectionItem(
                    status = getString(R.string.ongoing),
                    drawable = R.drawable.ongoing,
                    text = getString(R.string.orders_ongoing_desc)),
                OrderSelectionItem(
                    status = getString(R.string.completed),
                    drawable = R.drawable.completed,
                    text= getString(R.string.orders_completed_desc)),
                OrderSelectionItem(
                    status = getString(R.string.canceled),
                    drawable = R.drawable.canceled,
                    text=getString(R.string.orders_canceled_desc))
            )
        )
    }

//    private fun initOrdersObserver() {
//        ordersViewModel.ordersLiveData.observe(viewLifecycleOwner) {
//            ordersAdapter.submitOrdersData(it)
//        }
//    }
}