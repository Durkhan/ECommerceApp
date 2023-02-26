package com.tasks.ecommerceapp.registration


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.tasks.ecommerceapp.databinding.FragmentCreateEmailPasswordAccountBinding
import com.tasks.ecommerceapp.utils.CheckViewsValid
import com.tasks.ecommerceapp.utils.Results
import com.tasks.ecommerceapp.utils.isEmail
import com.tasks.ecommerceapp.utils.randomNumber
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignupEmailAndPasswordAccount:Fragment() {
    private var _binding: FragmentCreateEmailPasswordAccountBinding?=null
    private val binding get()=_binding
    private val viewModel: CustomerViewModel by activityViewModels()
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
        _binding = FragmentCreateEmailPasswordAccountBinding.inflate(inflater, container, false)
        return binding?.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.email?.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(emailText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email=emailText.toString()
                checkEmailorPasswordisValid(binding?.email as EditText)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding?.password?.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(passwordText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                password=passwordText.toString()
                checkEmailorPasswordisValid(binding?.password as EditText)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding?.signup?.setOnClickListener {
            sendVerificationNumber(email)
        }
        findNavController().navigate(R.id.action_signupAccount_to_verifyFragment)

        requireActivity().onBackPressedDispatcher.addCallback{
            findNavController().popBackStack(R.id.createAccountFragment,false)
        }
    }



    private fun checkEmailorPasswordisValid(editText:EditText) {
        checksViewValids.checkFocusedEdittext(editText)
        if(email.isBlank() || password.isBlank()|| !isEmail(email) || password.length<7){
               checksViewValids.notEnabled(binding?.signup)
        }else{
            checksViewValids.setEnabled(binding?.signup)
        }
    }
    private fun registerCustomer() {
        val email = binding?.email?.text.toString()
        val password = binding?.password?.text.toString()
        val firstName = viewModel.firstName
        val lastName = viewModel.lastName
        val userName = viewModel.userName
        viewModel.registerCustomer(email,password,firstName,lastName,userName)
        viewModel.registerResult.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Results.Success -> {
                    val response = result.data
                    sendVerificationNumber(email)
                }
                is Results.Error -> {
                    Log.d("ResultError",result.exception)

                }
            }
        })
    }
    private fun sendVerificationNumber(toEmail: String) {
        val verificationCode = randomNumber()
        findNavController().navigate(R.id.action_signupAccount_to_verifyFragment)
        viewModel.toEmail=toEmail
        viewModel.verificationCode=verificationCode.toString()
        viewModel.sendVerificationNumber(toEmail,verificationCode)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}
