package com.tasks.ecommerceapp.ui.fragment

import android.os.Bundle
import android.view.View
import com.tasks.ecommerceapp.base.BaseViewBindingFragment
import com.tasks.ecommerceapp.databinding.FragmentHomeProductsBinding


class HomeProductsFragment : BaseViewBindingFragment<FragmentHomeProductsBinding>() {

    override fun getViewBinding() = FragmentHomeProductsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}