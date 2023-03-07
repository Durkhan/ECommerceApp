package com.tasks.ecommerceapp.presentation.signin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentSignInBinding
import com.tasks.ecommerceapp.common.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SigninFragment:Fragment() {
    private var _binding: FragmentSignInBinding?=null
    private val binding get()=_binding!!
    private var email =""
    private var password =""
    private var rememberMe=false
    private val signInViewModel: SignInViewModel by viewModels()
    private val checkViewsValid: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.email.addTextChangedListener(object: EmptyTextWatcher() {
            @SuppressLint("NewApi", "UseCompatTextViewDrawableApis")
            override fun onTextChanged(emailText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email=emailText.toString()
                emailOrPasswordIsValid(binding.email)
            }

        })
        binding.password.addTextChangedListener(object: EmptyTextWatcher() {
            override fun onTextChanged(passwordText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                password=passwordText.toString()
                emailOrPasswordIsValid(binding.password)
            }
        })


        binding.signin.setOnClickListener {
            signIn()
        }
       binding.signup.setOnClickListener {
           findNavController().navigate(R.id.createAccountFragment)
       }
       requireActivity().onBackPressedDispatcher.addCallback {
           requireActivity().finishAffinity()
       }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.createAccountFragment)
        }

        changePassword()
        checkRemember()


    }

    private fun changePassword() {
        binding.forgotPassword.setOnClickListener {
              findNavController().navigate(R.id.action_signinFragment_to_getRecoveryFragment)
        }
    }
    private fun checkRemember() {
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            rememberMe = isChecked
        }
    }

    private fun signIn() {
        signInViewModel.signIn(email, password)
        signInViewModel.signInResult.observe(viewLifecycleOwner) { result ->
            when(result){
                is Results.Success -> {
                    signInViewModel.saveToken(result.data.token.toString())
                    signInViewModel.saveRememberMe(rememberMe)
                    findNavController().navigate(R.id.profileFragment)
                }
                is Results.Error -> {
                    Toast.makeText(context,result.exception,Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun emailOrPasswordIsValid(editText:EditText) {
        checkViewsValid.checkFocusedEdittext(editText)
        if(!isEmail(email) || password.length<7){
           checkViewsValid.notEnabled(binding.signin)
        }
        else{
           checkViewsValid.setEnabled(binding.signin)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}