package com.tasks.ecommerceapp.presentation.my_profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.Constants.START_DESTINATION_CHANGED
import com.tasks.ecommerceapp.common.DARK_MODE
import com.tasks.ecommerceapp.common.Results
import com.tasks.ecommerceapp.databinding.FragmentMyProfilBinding
import com.tasks.ecommerceapp.presentation.MainActivity
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyProfileFragment : BaseViewBindingFragment<FragmentMyProfilBinding>(){

    override fun getViewBinding()=FragmentMyProfilBinding.inflate(layoutInflater)
    private val viewModel: MyProfileViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecycleView()
        getCustomer()
        editProfile()
        returnBack()
        setDarkMode()
        setSpinner()
    }



    private fun setSpinner() {
        val items= listOf("US","UK","AZ","TR","DE")
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_text,
            items
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
    }

    private fun setDarkMode() {
        binding.dark.setOnClickListener {
            if (!DARK_MODE){
                viewModel.setDarkMode(true)
                DARK_MODE=true
                requireActivity().recreate()
            }
        }

        binding.light.setOnClickListener {
            if(DARK_MODE){
                viewModel.setDarkMode(false)
                DARK_MODE=false
                requireActivity().recreate()
            }
        }
    }

    private fun returnBack() {
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun editProfile() {
        binding.editProfile.setOnClickListener {
            val intent=Intent(requireActivity(),MainActivity::class.java)
            intent.putExtra("edit",START_DESTINATION_CHANGED)
            startActivity(intent)
        }
    }

    private fun getCustomer() {
        viewModel.getCustomer()
        viewModel.customer.observe(viewLifecycleOwner){result->
            when(result){
                is Results.Success -> {
                    val response=result.data
                    viewModel.toEmail=response.email.toString()
                    viewModel.login=response.login.toString()
                    Glide.with(requireContext())
                        .load(response.avatarUrl)
                        .error(R.drawable.user_photo)
                        .placeholder(R.drawable.user_photo)
                        .transform(CircleCrop())
                        .into(binding.userPhoto)
                }
            }
        }
    }

    private fun initializeRecycleView() {
        var texts= mutableListOf(
            getString(R.string.adress),
            getString(R.string.notification),
            getString(R.string.payment),
            getString(R.string.security),
            getString(R.string.privacy_policy),
            getString(R.string.help_center),
            getString(R.string.change_the_password),
        )
        val icons= mutableListOf(
            R.drawable.ic_address,
            R.drawable.ic_nofication,
            R.drawable.ic_payment,
            R.drawable.ic_security,
            R.drawable.ic_privacy_policy,
            R.drawable.ic_help_center,
            R.drawable.ic_log_out,
        )
        val adapter = MyProfileItemsAdapter(texts,icons)
        val layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        }


}