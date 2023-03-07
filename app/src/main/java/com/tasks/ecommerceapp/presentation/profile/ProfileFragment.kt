package com.tasks.ecommerceapp.presentation.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.*
import com.tasks.ecommerceapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment :Fragment() {
    private var _binding: FragmentProfileBinding?=null
    private val binding get()=_binding
    private val viewModel: UpdateCustomerViewModel by viewModels()
    private val checkViewsValid: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }
    private var fullname=""
    private var phoneNumber=""
    private var userName=""
    private var email=""
    private var firstName=""
    private var lastName=""
    private var pickImageLauncher: ActivityResultLauncher<String>? =null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentProfileBinding.inflate(inflater,container,false)
        return binding?.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.fullName?.addTextChangedListener(object :EmptyTextWatcher(){
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                fullname=p0.toString()
                checkEdittext()
            }
        })
        binding?.email?.addTextChangedListener(object :EmptyTextWatcher(){
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email=p0.toString()
                checkEdittext()
            }
        })
        binding?.telephone?.addTextChangedListener(object :EmptyTextWatcher(){
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                phoneNumber=p0.toString()
                checkEdittext()
            }
        })
        binding?.nickName?.addTextChangedListener(object :EmptyTextWatcher(){
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                userName=p0.toString()
                checkEdittext()
            }
        })

         skipUpdate()
         updateCustomer()
         choosePhoto()


         binding?.back?.setOnClickListener {
             findNavController().popBackStack()
         }

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                viewModel.selectedImage.postValue(uri)
            }
        }



    }

    private fun choosePhoto() {
        binding?.choosePhoto?.setOnClickListener {
            selectImageFromGallery()
            viewModel.selectedImage.observe(viewLifecycleOwner) { uri ->
                if (uri != null) {
                    Glide.with(this)
                        .load(uri)
                        .transform(CircleCrop())
                        .into(binding?.userPhoto!!)
                }
            }
        }
    }

    private fun skipUpdate() {
        binding?.skip?.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_SetPinFragment)
        }
    }

    private fun updateCustomer() {
        binding?.mcontinue?.setOnClickListener {
            val date = binding?.date?.text.toString()
            val gender = binding?.gender?.text.toString()
            lifecycleScope.launch {
                var avatarUrl = ""
                if (viewModel.selectedImage.value != null) {
                    avatarUrl = viewModel.getUploadedImageUrl(
                        viewModel.selectedImage.value!!,
                        requireContext()
                    )
                }
                viewModel.updateCustomer(
                    viewModel.getToken(),
                    email = email,
                    gender = gender,
                    firstName = firstName,
                    lastName = lastName,
                    userName = userName,
                    avatarUrl = avatarUrl,
                    date = date,
                    telephone = phoneNumber
                )
            }
            viewModel.updateCustomerResult.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Results.Success -> {
                        Log.d("updateCustomerResult", "" + result.data)
                        findNavController().navigate(R.id.SetPinFragment)
                    }
                    is Results.Error -> {
                        Log.d("updateCustomerResult", "" + result.exception)
                    }
                }
            }
        }
    }

    private fun selectImageFromGallery() {
        pickImageLauncher?.launch("image/*")
    }



    private fun checkEdittext() {
        if (fullname.contains(" ")) {
            firstName = fullname.substring(0, fullname.indexOf(" "))
            lastName = fullname.substring(fullname.indexOf(" ") + 1, fullname.length)
        }
        if (!isName(userName) || !isName(firstName) || !isName(lastName) || !isEmail(email) || !isPhoneNumber(phoneNumber) || userName.length>=10)
            checkViewsValid.notEnabled(binding?.mcontinue)
        else
            checkViewsValid.setEnabled(binding?.mcontinue)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}





