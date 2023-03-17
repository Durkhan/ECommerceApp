package com.tasks.ecommerceapp.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.DARK_MODE
import com.tasks.ecommerceapp.databinding.ActivityProductsBinding
import com.tasks.ecommerceapp.extensions.hide
import com.tasks.ecommerceapp.extensions.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DARK_MODE){
            setTheme(R.style.DarkTheme)
            WindowInsetsControllerCompat(window,window.decorView)
                .isAppearanceLightStatusBars=false
        }
        else{
            setTheme(R.style.LightTheme)
        }
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureBottomNavVisibility()
        navigateFragmentsFromBottomView()
    }

    private fun navigateFragmentsFromBottomView() {
        val navController = findNavController(R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        binding.bottomNav.setOnItemReselectedListener { item ->
            if (item.itemId != binding.bottomNav.selectedItemId)
                NavigationUI.onNavDestinationSelected(item, navController)
        }

        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeProductsFragment)
                    true
                }
                R.id.orders -> {
                    navController.navigate(R.id.ordersFragment)
                    true
                }
                R.id.card -> {
                    navController.navigate(R.id.cardFragment)
                    true
                }
                R.id.profile -> {
                    navController.navigate(R.id.myProfileFragment)
                    true
                }
                else -> false
            }
        }


    }

    private fun configureBottomNavVisibility() = with(binding) {
        findNavController(R.id.fragmentContainerView).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.productFullDetailFragment,
                R.id.changePasswordFragment,
                R.id.getRecoveryFragment,
                R.id.recoveryFragment-> bottomNav.hide()

                else -> {
                    bottomNav.show()
                }
            }
        }
    }

}