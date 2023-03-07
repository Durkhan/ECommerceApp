package com.tasks.ecommerceapp.presentation.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentCreateAccountBinding
import com.tasks.ecommerceapp.common.CheckViewsValid
import com.tasks.ecommerceapp.common.EmptyTextWatcher
import com.tasks.ecommerceapp.common.isName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment:Fragment() {
    private var _binding: FragmentCreateAccountBinding?=null
    private val binding get()=_binding!!
    private var firstName =""
    private var lastName =""
    private var userName =""
    private val registrationViewModel: ActivityCustomerViewModel by activityViewModels()
    private val checkViewsValid: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.firstName.addTextChangedListener(object : EmptyTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                firstName=s.toString()
                checkEdittextNotEmpty(binding.firstName)
            }
        })
        binding.lastName.addTextChangedListener(object : EmptyTextWatcher()  {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lastName=s.toString()
                checkEdittextNotEmpty(binding.lastName)
            }
        })
        binding.userName.addTextChangedListener(object : EmptyTextWatcher()  {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                userName=s.toString()
                checkEdittextNotEmpty(binding.userName)
            }


        })
         binding.mcontinue.setOnClickListener {
             registrationViewModel.firstName=firstName
             registrationViewModel.lastName=lastName
             registrationViewModel.userName=userName
             findNavController().navigate(R.id.action_createAccountFragment_to_signupAccount)
         }

        binding.signIn.setOnClickListener {
            findNavController().navigate(R.id.action_createAccountFragment_to_signinFragment)
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finishAffinity()
        }
    }


    private fun checkEdittextNotEmpty(editText: EditText) {
        checkViewsValid.checkFocusedEdittext(editText)
       if(!isName(firstName) || !isName(lastName) || !isName(userName) || userName.length>=10) {
           checkViewsValid.notEnabled(binding.mcontinue)
       }else{
           checkViewsValid.setEnabled(binding.mcontinue)
       }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}