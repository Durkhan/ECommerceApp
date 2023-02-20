package com.tasks.ecommerceapp.intro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenFragment :Fragment() {
    private  var _binding: FragmentSplashScreenBinding?=null
    private val binding get()=_binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scaleAnimation = ScaleAnimation(
            1f, 40f,
            1f, 40f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = 1000 // set the duration to 1 second
        CoroutineScope(Dispatchers.Main).launch {
            binding?.imageView?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate))
            delay(1000)
            binding?.imageView?.startAnimation(scaleAnimation)
            delay(1000)
            try {
                findNavController().navigate(R.id.action_spllashScreenFragment_to_welcomeFragment)
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