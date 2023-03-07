package com.tasks.ecommerceapp.presentation.recovery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentGetRecoveryBinding
import com.tasks.ecommerceapp.presentation.registration.ActivityCustomerViewModel
import com.tasks.ecommerceapp.common.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GetRecoveryFragment:Fragment() {
    private var _binding:FragmentGetRecoveryBinding?=null
    private val binding get()=_binding!!
    private var toEmail=""
    private val viewModel: ActivityCustomerViewModel by activityViewModels()
    private val checkViewsValid: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGetRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userNameOrEmail.addTextChangedListener(object :EmptyTextWatcher(){
            override fun onTextChanged(emailOrusername: CharSequence?, p1: Int, p2: Int, p3: Int) {
                toEmail=emailOrusername.toString()
                checkEdittext(binding.userNameOrEmail)
            }
        })
        binding.mcontinue.setOnClickListener {
            recoveryAccount()
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
    }

    private fun checkEdittext(editText:EditText) {
        checkViewsValid.checkFocusedEdittext(editText)
        if(isName(toEmail) || isEmail(toEmail)){
            checkViewsValid.setEnabled(binding.mcontinue)
        }
        else{
            checkViewsValid.notEnabled(binding.mcontinue)
        }
    }
    private fun recoveryAccount() {
        toEmail=binding.userNameOrEmail.text.toString()
        lifecycleScope.launch{
            viewModel.getCustomer(viewModel.getToken(),toEmail)
        }
        viewModel.customer.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Results.Success -> {
                    val customer = result.data
                    val verificationCode= randomNumber()
                    viewModel.verificationCode= verificationCode.toString()
                    viewModel.toEmail=customer.email.toString( )
                    if(toEmail==customer.email || toEmail==customer.login){
                        viewModel.sendVerificationNumber(customer.email.toString(),verificationCode)
                        findNavController().navigate(R.id.recoveryFragment)
                    }
                    else{
                        binding.userNameOrEmail.error=getString(R.string.please_write_correct_username)
                    }

                }
                is Results.Error -> {
                    val errorMessage = result.exception
                    Log.d("Error Message",""+errorMessage)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}