package com.tasks.ecommerceapp.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.ActivityProductsBinding
import com.tasks.ecommerceapp.extensions.hide
import com.tasks.ecommerceapp.extensions.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureBottomNavVisibility()
    }

    private fun configureBottomNavVisibility() = with(binding) {
        findNavController(R.id.fragmentContainerView).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.productFullDetailFragment -> bottomNav.hide()
                else -> {
                    bottomNav.show()
                }
            }
        }
    }
}