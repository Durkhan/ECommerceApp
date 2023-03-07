package com.tasks.ecommerceapp.presentation.intro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentSplashScreenBinding
import com.tasks.ecommerceapp.presentation.base.ProductsActivity
import com.tasks.ecommerceapp.presentation.registration.ActivityCustomerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenFragment :Fragment() {
    private  var _binding: FragmentSplashScreenBinding?=null
    private val binding get()=_binding!!
    private val viewModel:ActivityCustomerViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                if (viewModel.getFirstTime()){
                    findNavController().navigate(R.id.action_spllashScreenFragment_to_welcomeFragment)
                }
                else if (!viewModel.getRememberUser() || viewModel.getToken().isBlank()){
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}