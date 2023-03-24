package com.tasks.ecommerceapp.presentation.registration


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.*
import com.tasks.ecommerceapp.databinding.FragmentSignUpBinding
import com.tasks.ecommerceapp.presentation.verify.VerificationModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignupFragment:Fragment() {

    private var _binding: FragmentSignUpBinding?=null
    private val binding get()=_binding!!
    private val viewModel: SignUpViewModel by viewModels()
    private var email =""
    private var password =""
    private val args:SignupFragmentArgs by navArgs()
    private val checksViewValid: CheckViewsValid by lazy { CheckViewsValid(requireContext()) }

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

        binding.signup.setOnClickListener { registerCustomer() }

        binding.signIn.setOnClickListener { findNavController().navigate(R.id.action_signupAccount_to_signinFragment) }

        binding.back.setOnClickListener { findNavController().popBackStack() }

        requireActivity().onBackPressedDispatcher.addCallback{ findNavController().popBackStack() }
    }



    private fun emailOrPasswordIsValid(editText:EditText) {
        checksViewValid.checkFocusedEdittext(editText)
        if(!isEmail(email) || password.length<7){
               checksViewValid.notEnabled(binding.signup)
        }else{
            checksViewValid.setEnabled(binding.signup)
        }
    }
    private fun registerCustomer() {
        val firstName = args.userInfo?.firstName.toString()
        val lastName = args.userInfo?.lastName.toString()
        val userName = args.userInfo?.login.toString()

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
        val verificationData = VerificationModel(toEmail,verificationCode.toString())
        viewModel.sendVerificationNumber(toEmail,verificationCode)
        val action=SignupFragmentDirections.actionSignupAccountToVerifyFragment(verificationData)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}
