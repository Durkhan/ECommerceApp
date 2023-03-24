package com.tasks.ecommerceapp.presentation.change_password

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.*
import com.tasks.ecommerceapp.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChangePasswordFragment:Fragment() {
    private var _binding: FragmentChangePasswordBinding?=null
    private val binding get()=_binding!!
    private val viewModel: ChangePasswordViewModel by viewModels()
    private var oldPassword=""
    private var newPassword=""
    private val checkViewsValid: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentChangePasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.oldPassword.addTextChangedListener(object: EmptyTextWatcher() {
            override fun onTextChanged(passwordText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                oldPassword=passwordText.toString()
                emailOrPasswordIsValid(binding.oldPassword)
            }
        })
        binding.newPassword.addTextChangedListener(object:EmptyTextWatcher(){
            override fun onTextChanged(passwordText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                newPassword=passwordText.toString()
                emailOrPasswordIsValid(binding.newPassword)
            }
        })

        binding.change.setOnClickListener {
            oldPassword=binding.oldPassword.text.toString()
            newPassword=binding.newPassword.text.toString()
            changePassword(oldPassword,newPassword)
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack(R.id.getRecoveryFragment,false)
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack(R.id.getRecoveryFragment,false)
        }

    }

    private fun changePassword(oldPassword: String, newPassword: String) {
           lifecycleScope.launch {
               viewModel.changePassword(oldPassword,newPassword)
           }
            viewModel.changePasswordResult.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Results.Success -> {
                        if (result.data.message!=null){
                            showDialog()
                        }
                        else{
                            binding.oldPassword.error=getString(R.string.password_is_incorrect)
                        }

                    }
                    is Results.Error -> {
                        Log.d("ResultError",""+result.exception)
                    }
                }
            }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_alert, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        lifecycleScope.launch {
            dialog.show()
            delay(2000)
            dialog.dismiss()
            findNavController().navigate(R.id.myProfileFragment)
        }
    }


    private fun emailOrPasswordIsValid(editText: EditText) {
        checkViewsValid.checkFocusedEdittext(editText)
        if (oldPassword.length<7 || newPassword.length<7 || oldPassword.length>30 || newPassword.length>30){
               checkViewsValid.notEnabled(binding.change)
        } else{
            checkViewsValid.setEnabled(binding.change)
    }
    }


}