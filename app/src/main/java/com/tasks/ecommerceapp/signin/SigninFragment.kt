package com.tasks.ecommerceapp.signin

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.protobuf.Empty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentSignInBinding
import com.tasks.ecommerceapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SigninFragment:Fragment() {
    private var _binding: FragmentSignInBinding?=null
    private val binding get()=_binding
    private var email =""
    private var password =""
    private val signInViewModel: SignInViewModel by viewModels()
    private val checksViewValids: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding?.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.email?.addTextChangedListener(object: EmptyTextWatcher() {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @SuppressLint("NewApi", "UseCompatTextViewDrawableApis")
            override fun onTextChanged(emailText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email=emailText.toString()
                checkEmailorPasswordisValid(binding?.email as EditText)

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding?.password?.addTextChangedListener(object: EmptyTextWatcher() {
            override fun onTextChanged(passwordText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                password=passwordText.toString()
                checkEmailorPasswordisValid(binding?.password as EditText)
            }

        })
        binding?.signin?.setOnClickListener {
            signIn()
        }


        forgotPassword()

    }

    private fun forgotPassword() {
        binding?.forgotPassword?.setOnClickListener {
              findNavController().navigate(R.id.action_signinFragment_to_getRecoveryFragment)
        }
    }

    private fun signIn() {
        signInViewModel.signIn(email, password)
        signInViewModel.signInResult.observe(viewLifecycleOwner) { result ->
            when(result){
                is Results.Success -> {
                    val dataStoreManager = DataStoreManager(requireContext().datastore)
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStoreManager.saveToken(result.data.token.toString())
                    }
                    findNavController().navigate(R.id.action_signinFragment_to_profileFragment)
                }
                is Results.Error -> {
                    Toast.makeText(context,result.exception,Toast.LENGTH_LONG).show()
                }
            }

        }
    }


    private fun checkEmailorPasswordisValid(editText:EditText) {
        checksViewValids.checkFocusedEdittext(editText)
        if(email.isBlank() || password.isBlank()|| password.length<7){
           checksViewValids.notEnabled(binding?.signin)
        }
        else{
           checksViewValids.setEnabled(binding?.signin)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}