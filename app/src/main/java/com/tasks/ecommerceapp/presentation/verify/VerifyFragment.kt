package com.tasks.ecommerceapp.presentation.verify

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentVerifyBinding
import com.tasks.ecommerceapp.presentation.registration.ActivityCustomerViewModel
import com.tasks.ecommerceapp.common.CheckViewsValid
import com.tasks.ecommerceapp.common.EmptyTextWatcher
import com.tasks.ecommerceapp.common.obfuscateString
import com.tasks.ecommerceapp.common.randomNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyFragment:Fragment() {
    private var _binding: FragmentVerifyBinding?=null
    private val binding get()=_binding
    private var verifyCode =""
    private val viewModel: ActivityCustomerViewModel by activityViewModels()
    private val checkViewsValid: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding?.root    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val toEmail=viewModel.toEmail
        val length=toEmail.length
        val obfuscatedEmail = obfuscateString(toEmail)

        val spannableString = SpannableString(getString(R.string.authorization_code_has_sent)+" "+obfuscatedEmail)
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.carrot)),spannableString.length-length,spannableString.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding?.verifingemail?.text=spannableString

        binding?.etxtPinEntry?.addTextChangedListener(object :EmptyTextWatcher(){
            override fun onTextChanged(pinText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                  verifyCode =pinText.toString()
                  checkVerifedCode()
            }
        })

        setTimer().start()

        binding?.verify?.setOnClickListener {
              if (verifyCode==viewModel.verificationCode){
                  setTimer().cancel()
                  findNavController().navigate(R.id.action_verifyFragment_to_signinFragment)
              }
            else{
                  Toast.makeText(context,getString(R.string.verification_code_error_messase),Toast.LENGTH_LONG).show()
            }
        }


        binding?.resend?.setOnClickListener {
            val verificationCode = randomNumber()
            viewModel.verificationCode=verificationCode.toString()
            viewModel.sendVerificationNumber(toEmail,verificationCode)
            setTimer().start()
        }


    }

    private fun setTimer():CountDownTimer {
        val millisInFuture: Long = 60000 // 60 seconds
        val countDownInterval: Long = 1000 // 1 second
        binding?.resend?.isEnabled=false
        val timer = object: CountDownTimer(millisInFuture, countDownInterval) {
            @SuppressLint("SetTextI18n", "ResourceType")
            override fun onTick(millisUntilFinished: Long) {

                val secondsRemaining = millisUntilFinished / 1000
                val spannableString = SpannableString(getString(R.string.resend_code_in)+" "+"$secondsRemaining"+" s")
                spannableString.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.carrot)), spannableString.length-secondsRemaining.toString().length-2 ,spannableString.length-1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding?.resend?.text = spannableString
            }

            override fun onFinish() {
                // Countdown is finished
                binding?.resend?.text=getString(R.string.resend_code)
                binding?.resend?.isEnabled=true
            }
        }

   return timer

    }


    private fun checkVerifedCode() {
        if(verifyCode.length!=4){
            checkViewsValid.notEnabled(binding?.verify)
        }
        else{
            checkViewsValid.setEnabled(binding?.verify)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}