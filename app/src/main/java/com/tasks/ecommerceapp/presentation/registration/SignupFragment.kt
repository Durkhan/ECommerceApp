package com.tasks.ecommerceapp.presentation.registration


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.*
import com.tasks.ecommerceapp.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignupFragment:Fragment() {
    private var _binding: FragmentSignUpBinding?=null
    private val binding get()=_binding!!
    private val viewModel: ActivityCustomerViewModel by activityViewModels()
    private var email =""
    private var password =""

    private val checksViewValids: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.email.addTextChangedListener(object:EmptyTextWatcher(){
            override fun onTextChanged(emailText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email=emailText.toString()
                emailOrPasswordIsValid(binding.email)
            }
        })
        binding.password.addTextChangedListener(object:EmptyTextWatcher(){
            override fun onTextChanged(passwordText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                password=passwordText.toString()
                emailOrPasswordIsValid(binding.password)
            }
        })

        binding.signup.setOnClickListener {
            registerCustomer()
        }
       binding.signIn.setOnClickListener {
           findNavController().navigate(R.id.action_signupAccount_to_signinFragment)
       }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback{
            findNavController().popBackStack()
        }
    }



    private fun emailOrPasswordIsValid(editText:EditText) {
        checksViewValids.checkFocusedEdittext(editText)
        if(!isEmail(email) || password.length<7){
               checksViewValids.notEnabled(binding.signup)
        }else{
            checksViewValids.setEnabled(binding.signup)
        }
    }
    private fun registerCustomer() {
        val firstName = viewModel.firstName
        val lastName = viewModel.lastName
        val userName = viewModel.userName
        viewModel.registerCustomer(email,password,firstName,lastName,userName)
        viewModel.registerResult.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Results.Success -> {
                    val response = result.data
                    sendVerificationNumber(response.email.toString())
                }
                is Results.Error -> {
                    Log.d("ResultError",result.exception)
                    binding.email.error = getString(R.string.user_already_exists)
                }
            }
        })
    }
    private fun sendVerificationNumber(toEmail: String) {
        val verificationCode = randomNumber()
        viewModel.toEmail=toEmail
        viewModel.verificationCode=verificationCode.toString()
        viewModel.sendVerificationNumber(toEmail,verificationCode)
        findNavController().navigate(R.id.action_signupAccount_to_verifyFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}
