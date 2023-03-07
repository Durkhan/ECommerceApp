package com.tasks.ecommerceapp.presentation.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentRegistrationBinding

class RegistrationFragment:Fragment(){
    private var _binding: FragmentRegistrationBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signInWithPassword.setOnClickListener {
            findNavController().navigate(R.id.action_registration_to_signinFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finishAffinity()
        }

        binding.signup.setOnClickListener {
             findNavController().navigate(R.id.action_registration_to_createAccountFragment)
         }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}