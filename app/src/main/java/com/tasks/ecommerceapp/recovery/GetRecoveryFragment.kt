package com.tasks.ecommerceapp.recovery

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentGetRecoveryBinding
import com.tasks.ecommerceapp.registration.CustomerViewModel
import com.tasks.ecommerceapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GetRecoveryFragment:Fragment() {
    private var _binding:FragmentGetRecoveryBinding?=null
    private val binding get()=_binding
   private var toEmail=""
    private lateinit var dataStoreManager: DataStoreManager
    private val viewmodel: CustomerViewModel by activityViewModels()
    private val checksViewValids: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGetRecoveryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.userNameOrEmail?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(emailOrusername: CharSequence?, p1: Int, p2: Int, p3: Int) {
                toEmail=emailOrusername.toString()
                checkEdittext()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding?.mcontinue?.setOnClickListener {
            recoveryAccount()
        }



    }

    private fun checkEdittext() {
        if(toEmail.isBlank()){
            checksViewValids.notEnabled(binding?.mcontinue)

        }
        else{
            checksViewValids.setEnabled(binding?.mcontinue)
        }

    }

    private fun recoveryAccount() {
        dataStoreManager=DataStoreManager(requireContext().datastore)
        toEmail=binding?.userNameOrEmail?.text.toString()
        lifecycleScope.launch{
            viewmodel.getCustomer(dataStoreManager.token.first(),toEmail)
        }

        viewmodel.customer.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Results.Success -> {
                    val customer = result.data
                    val verificationCode= randomNumber()
                    viewmodel.verificationCode= verificationCode.toString()
                    viewmodel.toEmail=customer.email.toString()
                    viewmodel.password=result.data.password.toString()
                    viewmodel.sendVerificationNumber(customer.email.toString(),verificationCode)
                    findNavController().navigate(R.id.action_getRecoveryFragment_to_recoveryFragment)
                }
                is Results.Error -> {
                    val errorMessage = result.exception
                    Log.d("Error Message",""+errorMessage)
                    findNavController().navigate(R.id.action_getRecoveryFragment_to_recoveryFragment)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}