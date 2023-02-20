package com.tasks.ecommerceapp.intro


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentWelcomeBinding

class WelcomeFragment:Fragment() {
    private var _binding:FragmentWelcomeBinding?=null
    private val binding get()=_binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding?.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.constraint?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce))
        binding?.mcontinue?.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_stepsFragment)
        }
        requireActivity().onBackPressedDispatcher.addCallback{
            requireActivity().finishAffinity()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
