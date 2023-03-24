package com.tasks.ecommerceapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.Constants
import com.tasks.ecommerceapp.common.Constants.START_DESTINATION_CHANGED
import com.tasks.ecommerceapp.common.DARK_MODE
import com.tasks.ecommerceapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DARK_MODE)
            setTheme(R.style.DarkTheme)
        else
            setTheme(R.style.LightTheme)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        if (intent?.getStringExtra("edit") == START_DESTINATION_CHANGED)
            graph.setStartDestination(R.id.profileFragment)
        else
            graph.setStartDestination(R.id.spllashScreenFragment)


        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)


    }

}