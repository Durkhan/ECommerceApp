package com.tasks.ecommerceapp.presentation.intro

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.DARK_MODE
import com.tasks.ecommerceapp.common.DataStoreManager
import com.tasks.ecommerceapp.common.LANGUAGE
import com.tasks.ecommerceapp.databinding.FragmentSplashScreenBinding
import com.tasks.ecommerceapp.presentation.base.ProductsActivity
import com.tasks.ecommerceapp.presentation.registration.ActivityCustomerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment :Fragment() {
    private  var _binding: FragmentSplashScreenBinding?=null
    private val binding get()=_binding!!

    @Inject
    lateinit var dataStoreManager:DataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch{
            DARK_MODE=dataStoreManager.isDarkMode.first()
          if(DARK_MODE) {
              requireActivity().setTheme(R.style.DarkTheme)
              val window=requireActivity().window
              window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
              window.statusBarColor = ContextCompat.getColor(requireContext(),R.color.background_dark)
              binding.constraint.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.background_dark))
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                  WindowInsetsControllerCompat(window,window.decorView)
                      .isAppearanceLightStatusBars = false
              }

          }
          else{
              requireActivity().setTheme(R.style.LightTheme)
          }
        }
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scaleAnimation = ScaleAnimation(
            1f, 40f,
            1f, 40f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = 800 // set the duration to 1 second
        CoroutineScope(Dispatchers.Main).launch {
            binding.imageView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate))
            delay(1000)
            binding.imageView.startAnimation(scaleAnimation)
            delay(700)
            try {
                setLocale(dataStoreManager.language.first())
                if (dataStoreManager.isFirstTime.first()){
                    findNavController().navigate(R.id.action_spllashScreenFragment_to_welcomeFragment)
                }
                else if (!dataStoreManager.isRemember.first()
                    || dataStoreManager.token.first().isBlank()
                    || isAccessTokenExpired())
                {
                    findNavController().navigate(R.id.action_spllashScreenFragment_to_signinFragment)
                }
                else{
                    requireActivity().startActivity(
                        Intent(
                            requireActivity(),
                            ProductsActivity::class.java
                        )
                    )
                }
            }catch (e:Exception){
                Log.d("Exception",e.message.toString())
            }
        }
    }

    private fun setLocale(language: String) {
        LANGUAGE=language
        val locale= Locale(language)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private suspend fun isAccessTokenExpired(): Boolean {
        return try{
            val token=dataStoreManager.token.first().removePrefix("Bearer ")
            val jwt: DecodedJWT = JWT.decode(token)
            val expirationTime: Date = jwt.expiresAt
            Date().after(expirationTime)
        }catch (e:Exception){
            Log.d("AccessTokenDecoded",e.message.toString())
            false
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}