package com.tasks.ecommerceapp.presentation.recovery

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
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentRecoveryBinding
import com.tasks.ecommerceapp.common.CheckViewsValid
import com.tasks.ecommerceapp.common.EmptyTextWatcher
import com.tasks.ecommerceapp.common.obfuscateString
import com.tasks.ecommerceapp.common.randomNumber
import com.tasks.ecommerceapp.domain.usecases.registrion.SendVerificationUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecoveryFragment:Fragment() {
    private var _binding: FragmentRecoveryBinding?=null
    private val binding get()=_binding
    private var verifyCode =""
    private var sendVerifiedCode=""
    private val args:RecoveryFragmentArgs by navArgs()

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
        _binding = FragmentRecoveryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toEmail=args.userInfo?.email.toString()
        sendVerifiedCode=args.userInfo?.code.toString()
        val length=toEmail.length


        val spannableString = SpannableString(getString(R.string.recovery_code_has_been_sent_to)+" "+obfuscateString(toEmail))
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.carrot)),spannableString.length-length,spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding?.verifingemail?.text=spannableString
          binding?.verify?.isEnabled=true
        binding?.etxtPinEntry?.addTextChangedListener(object : EmptyTextWatcher() {
            override fun onTextChanged(pinText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                verifyCode =pinText.toString()
                checkVerifiedCode()
            }

        })

        setTimer().start()

        binding?.verify?.setOnClickListener {
            if (verifyCode==sendVerifiedCode){
                setTimer().cancel()
                findNavController().navigate(R.id.action_recoveryFragment_to_changePasswordFragment)
            }
            else{
                Toast.makeText(context,getString(R.string.verification_code_error_messase), Toast.LENGTH_LONG).show()
            }
        }


        binding?.resend?.setOnClickListener {
            val verificationCode = randomNumber()
            sendVerifiedCode=verificationCode.toString()
            lifecycleScope.launch {
                sendVerificationUseCase(toEmail,verificationCode)
            }

            setTimer().start()
        }

        binding?.back?.setOnClickListener {
            findNavController().popBackStack(R.id.getRecoveryFragment,false)
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack(R.id.getRecoveryFragment,false)
        }


    }

    private fun setTimer(): CountDownTimer {
        val millisInFuture: Long = 60000 // 60 seconds
        val countDownInterval: Long = 1000 // 1 second
        binding?.resend?.isEnabled=false
        val timer = object: CountDownTimer(millisInFuture, countDownInterval) {
            @SuppressLint("SetTextI18n", "ResourceType")
            override fun onTick(millisUntilFinished: Long) {

                val secondsRemaining = millisUntilFinished / 1000
                val spannableString = SpannableString(getString(R.string.resend_code_in)+" "+"$secondsRemaining"+" s")
                spannableString.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.carrot)), spannableString.length-secondsRemaining.toString().length-2 ,spannableString.length-1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
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

    private fun checkVerifiedCode() {
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