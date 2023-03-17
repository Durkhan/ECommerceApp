package com.tasks.ecommerceapp.presentation.orders

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasks.ecommerceapp.databinding.FragmentOrdersBinding
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.common.listener.CompleteOrderListener
import com.tasks.ecommerceapp.data.model.customer.orders.Order
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : BaseViewBindingFragment<FragmentOrdersBinding>(),CompleteOrderListener {


    private val ordersViewModel: OrdersViewModel by viewModels()
    override fun getViewBinding() = FragmentOrdersBinding.inflate(layoutInflater)
    private val ongoingOrders= mutableListOf<Order>()
    private val canceledOrders= mutableListOf<Order>()
    private val completedOrders= mutableListOf<Order>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOrdersObserver()
        returnBack()
        showingOrdersShippingTypes()
//        getOrderByOrderNo()
    }

    private fun showingOrdersShippingTypes() {
        binding.ibSelectionOngoing.isSelected=true
        binding.rvOrdersOngoing.isVisible=false
        binding.ibSelectionCanceled.isSelected=true
        binding.rvOrdersCanceled.isVisible=false
        binding.ibSelectionCompleted.isSelected=true
        binding.rvOrdersCompleted.isVisible=false

        binding.clOngoing.setOnClickListener {
            binding.rvOrdersOngoing.isVisible = !binding.rvOrdersOngoing.isVisible
            binding.ibSelectionOngoing.isSelected=!binding.rvOrdersOngoing.isVisible
        }
        binding.clStatusCompleted.setOnClickListener {
            binding.rvOrdersCompleted.isVisible = !binding.rvOrdersCompleted.isVisible
            binding.ibSelectionCompleted.isSelected=!binding.rvOrdersCompleted.isVisible
        }
        binding.clStatusCanceled.setOnClickListener {
            binding.rvOrdersCanceled.isVisible = !binding.rvOrdersCanceled.isVisible
            binding.ibSelectionCanceled.isSelected=!binding.rvOrdersCanceled.isVisible
        }
    }

    private fun returnBack() {
        binding.header.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }

    }

    private fun initRvOngoingAdapter(list:List<Order>) {
        val layoutManager = GridLayoutManager(requireContext(), RecyclerView.VERTICAL)
        binding.rvOrdersOngoing.layoutManager = layoutManager
        val adapter = OngoingOrdersAdapter(list, this)
        binding.rvOrdersOngoing.adapter = adapter
    }
    private fun initRvCanceledAdapter(list:List<Order>) {
        val layoutManager = GridLayoutManager(requireContext(), RecyclerView.VERTICAL)
        binding.rvOrdersCanceled.layoutManager = layoutManager
        val adapter = CanceledOrdersAdapter(list)
        binding.rvOrdersCanceled.adapter = adapter
    }
    private fun initRvCompletedAdapter(list:List<Order>) {
        val layoutManager = GridLayoutManager(requireContext(), RecyclerView.VERTICAL)
        binding.rvOrdersCompleted.layoutManager = layoutManager
        val adapter = CompletedOrdersAdapter(list, this)
        binding.rvOrdersCompleted.adapter = adapter
    }
    private fun initOrdersObserver() {
        ongoingOrders.clear()
        canceledOrders.clear()
        completedOrders.clear()
        ordersViewModel.getAllOrders()
        ordersViewModel.ordersLiveData.observe(viewLifecycleOwner) {result->
            when(result){
                is ProductsResults.Success ->{
                   val response=result.data
                    for (orders in response) {
                        if (orders.status=="completed"){
                            completedOrders.add(orders)
                        }
                        if (orders.canceled==true){
                            canceledOrders.add(orders)
                        }
                        else{
                            ongoingOrders.add(orders)
                        }
                    }
                    initRvOngoingAdapter(ongoingOrders)
                    initRvCanceledAdapter(canceledOrders)
                    initRvCompletedAdapter(completedOrders)
                }

                is ProductsResults.Loading -> {}

                is ProductsResults.Error ->{
                    Log.d("ProductsResultsError",result.exception)
                }
            }
        }
    }

    override fun clickCheckOut(order:Order) {
        ordersViewModel.updateOrder(order._id.toString(),order.email.toString(),"completed")
          ordersViewModel.updateOrderLiveData.observe(viewLifecycleOwner){result->
              when(result){
                  is ProductsResults.Success -> {
                      ongoingOrders.remove(order)
                      initRvOngoingAdapter(ongoingOrders)
                      completedOrders.add(order)
                      initRvCompletedAdapter(completedOrders)
                  }
                  is ProductsResults.Error -> {
                      Log.d("ProductsResultsError",result.exception)
                  }
                  else -> {}
              }
          }
    }


//    private fun getOrderByOrderNo() {
//        ordersViewModel.getOrderByOrderNo("7671650")
//        ordersViewModel.ordersOrderNoLiveData.observe(viewLifecycleOwner) {result->
//            when(result){
//                is ProductsResults.Success ->{
//                    Log.d("ProductsResErrobrOrder",""+result.data)
//                }
//
//                is ProductsResults.Loading -> {}
//
//                is ProductsResults.Error ->{
//                    Log.d("ProductsResultsError",result.exception)
//                }
//            }
//        }
//    }
}