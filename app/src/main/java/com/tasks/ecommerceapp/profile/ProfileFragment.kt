package com.tasks.ecommerceapp.profile
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.tasks.ecommerceapp.databinding.FragmentProfileBinding
import com.tasks.ecommerceapp.set_pin.PinActivity
import com.tasks.ecommerceapp.utils.CheckViewsValid
import com.tasks.ecommerceapp.utils.DataStoreManager
import com.tasks.ecommerceapp.utils.Results
import com.tasks.ecommerceapp.utils.datastore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment :Fragment() {
    private var _binding: FragmentProfileBinding?=null
    private val binding get()=_binding
    private val viewModel: UpdateCustomerViewModel by viewModels()
    private val checksViewValids: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }
    private var pickImageLauncher: ActivityResultLauncher<String>? =null
    private lateinit var dataStoreManager: DataStoreManager

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
        addEditextWatcher()

        binding?.mcontinue?.setOnClickListener {
         updateCustomer()
        }

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                viewModel.selectedImage.postValue(uri)
            }
        }

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

    private fun updateCustomer() {
        val fullName=binding?.fullName?.text.toString()
        val email=binding?.email?.text.toString()
        val userName=binding?.nickName?.text.toString()
        val date=binding?.date?.text.toString()
        val gender=binding?.gender?.text.toString()
        val telephone=binding?.telephone?.text.toString()
        val firstName=fullName.substring(0,fullName.indexOf(" "))
        val lastName=fullName.substring(fullName.indexOf(" ")+1,fullName.length)
        dataStoreManager=DataStoreManager(requireContext().datastore)
        lifecycleScope.launch {
            viewModel.updateCustomer(
                dataStoreManager.token.first(),
                email = email,
                gender = gender,
                firstName = firstName,
                lastName = lastName,
                userName = userName,
                avatarUrl = viewModel.selectedImage.value.toString(),
                date = date
            )

        }
            viewModel.updateCustomerResult.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Results.Success -> {

                        Log.d("updateCustomerResult", "" + result.data)
                        requireContext().startActivity(
                            Intent(
                                requireContext(),
                                PinActivity::class.java
                            )
                        )

                    }
                    is Results.Error -> {
                        Log.d("updateCustomerResult", "" + result.exception)
                    }
                }
            }

    }

    private fun selectImageFromGallery() {
        pickImageLauncher?.launch("image/*")
    }


    private fun addEditextWatcher() {
        val arrayList:ArrayList<EditText?> = arrayListOf(binding?.fullName,binding?.email,binding?.nickName,binding?.date,binding?.telephone,binding?.gender)

        for (editext in arrayList){
            editext?.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    checksViewValids.checkFocusedEdittext(editext)
                    checkEdittext(arrayList)
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
        }
    }
    private fun checkEdittext(arrayList: ArrayList<EditText?>) {

            if(arrayList[0]?.text!!.isBlank() || arrayList[1]?.text!!.isBlank() ||arrayList[2]?.text!!.isBlank()
                ||arrayList[3]?.text!!.isBlank() ||arrayList[4]?.text!!.isBlank() || arrayList[5]?.text!!.isBlank()){
                    checksViewValids.notEnabled(binding?.mcontinue)
                }
                else{
                    checksViewValids.setEnabled(binding?.mcontinue)
                }
            }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
        }





