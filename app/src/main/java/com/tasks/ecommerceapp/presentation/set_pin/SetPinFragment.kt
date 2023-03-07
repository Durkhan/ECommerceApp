package com.tasks.ecommerceapp.presentation.set_pin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmetSetPinBinding
import com.tasks.ecommerceapp.common.CheckViewsValid
import com.tasks.ecommerceapp.common.EmptyTextWatcher
import com.tasks.ecommerceapp.presentation.base.ProductsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetPinFragment :Fragment() {
    private var _binding: FragmetSetPinBinding?=null
    private val binding get()=_binding!!
    private var pin =""
    private val viewModel: SetPinViewModel by viewModels()
    private val checkViewsValid: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmetSetPinBinding.inflate(inflater,container,false)
         return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etxtPinEntry.addTextChangedListener(object : EmptyTextWatcher() {
            override fun onTextChanged(pinText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                pin =pinText.toString()
                checkVerifiedCode()
            }
        })
        binding.mconfirm.setOnClickListener {
             viewModel.savePin(pin)
            findNavController().navigate(R.id.action_SetPinFragment_to_SetFingerPrintFragment)
        }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.profileFragment)
        }

        binding.skip.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireActivity(),
                    ProductsActivity::class.java
                )
            )
        }
    }

    private fun checkVerifiedCode() {
        if(pin.length!=4){
            checkViewsValid.notEnabled(binding.mconfirm)
        }
        else{
            checkViewsValid.setEnabled(binding.mconfirm)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null

    }
}