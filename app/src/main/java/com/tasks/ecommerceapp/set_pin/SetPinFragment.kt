package com.tasks.ecommerceapp.set_pin

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmetSetPinBinding
import com.tasks.ecommerceapp.utils.CheckViewsValid
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetPinFragment :Fragment() {
    private var _binding: FragmetSetPinBinding?=null
    private val binding get()=_binding
    private var verifyCode =""
    private val viewModel: SetPinViewModel by viewModels()
    private val checksViewValids: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmetSetPinBinding.inflate(inflater,container,false)
         return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.etxtPinEntry?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(pinText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                verifyCode =pinText.toString()
                checkVerifedCode()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding?.mconfirm?.setOnClickListener {
             viewModel.savePin(verifyCode,requireContext())
            findNavController().navigate(R.id.action_SetPinFragment_to_SetFingerPrintFragment)
        }
    }

    private fun checkVerifedCode() {
        if(verifyCode.length!=4){
            checksViewValids.notEnabled(binding?.mconfirm)
        }
        else{
            checksViewValids.setEnabled(binding?.mconfirm)
        }
    }
}