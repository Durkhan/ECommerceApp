package com.tasks.ecommerceapp.set_pin

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.ActivityPinBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

    }

}