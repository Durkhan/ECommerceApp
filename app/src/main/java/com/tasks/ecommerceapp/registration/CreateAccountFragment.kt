package com.tasks.ecommerceapp.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentCreateAccountBinding
import com.tasks.ecommerceapp.utils.CheckViewsValid
import com.tasks.ecommerceapp.utils.EmptyTextWatcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment:Fragment() {
    private var _binding: FragmentCreateAccountBinding?=null
    private val binding get()=_binding
    private var firstName =""
    private var lastName =""
    private var userName =""
    private val registrationViewModel: CustomerViewModel by activityViewModels()
    private val checksViewValids: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding?.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding!!.mcontinue.isEnabled=true
        binding?.firstName?.addTextChangedListener(object : EmptyTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                firstName=s.toString()
                checkEdittextNotEmpty(binding?.firstName as EditText)
            }
        })
        binding?.lastName?.addTextChangedListener(object : EmptyTextWatcher()  {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lastName=s.toString()
                checkEdittextNotEmpty(binding?.lastName as EditText)

            }
        })
        binding?.userName?.addTextChangedListener(object : EmptyTextWatcher()  {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                userName=s.toString()
                checkEdittextNotEmpty(binding?.userName as EditText)
            }


        })
         binding?.mcontinue?.setOnClickListener {
             registrationViewModel.firstName=firstName
             registrationViewModel.lastName=lastName
             registrationViewModel.userName=userName
             findNavController().navigate(R.id.action_createAccountFragment_to_signinFragment)

         }
    }


    private fun checkEdittextNotEmpty(editText: EditText) {
        checksViewValids.checkFocusedEdittext(editText)
       if(firstName.isBlank() || lastName.isBlank() || userName.isBlank()) {
           checksViewValids.notEnabled(binding?.mcontinue)
       }else{
           checksViewValids.setEnabled(binding?.mcontinue)
       }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}