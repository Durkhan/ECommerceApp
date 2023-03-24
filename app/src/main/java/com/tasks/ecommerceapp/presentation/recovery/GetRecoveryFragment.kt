package com.tasks.ecommerceapp.presentation.recovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentGetRecoveryBinding
import com.tasks.ecommerceapp.common.*
import com.tasks.ecommerceapp.domain.usecases.registrion.SendVerificationUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class GetRecoveryFragment:Fragment() {
    private var _binding:FragmentGetRecoveryBinding?=null
    private val binding get()=_binding!!
    private var toEmail=""
    private val args:GetRecoveryFragmentArgs by navArgs()
    private val checkViewsValid: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }

    @Inject
    lateinit var sendVerificationUseCase: SendVerificationUseCase

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
        val email=args.userInfo?.email.toString()
        val login=args.userInfo?.login.toString()
        val verificationCode= randomNumber()
        if(toEmail==email || toEmail==login){
            val verifiedData = RecoveryPasswordModel(email,login, verificationCode.toString())
            val action = GetRecoveryFragmentDirections.actionGetRecoveryFragmentToRecoveryFragment(verifiedData)
            lifecycleScope.launch{
                sendVerificationUseCase(email,verificationCode)
            }
            findNavController().navigate(action)
        }
        else{
            binding.userNameOrEmail.error=getString(R.string.please_write_correct_username)
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}