package com.tasks.ecommerceapp.presentation.my_profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.Constants.START_DESTINATION_CHANGED
import com.tasks.ecommerceapp.common.DARK_MODE
import com.tasks.ecommerceapp.common.LANGUAGE
import com.tasks.ecommerceapp.common.Results
import com.tasks.ecommerceapp.common.listener.PasswordChangeListener
import com.tasks.ecommerceapp.databinding.FragmentMyProfilBinding
import com.tasks.ecommerceapp.presentation.MainActivity
import com.tasks.ecommerceapp.presentation.base.BaseViewBindingFragment
import com.tasks.ecommerceapp.presentation.recovery.RecoveryPasswordModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class MyProfileFragment : BaseViewBindingFragment<FragmentMyProfilBinding>(),PasswordChangeListener{

    override fun getViewBinding()=FragmentMyProfilBinding.inflate(layoutInflater)
    private val viewModel: MyProfileViewModel by viewModels()

    private var email:String = ""
    private var login:String = ""

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
        val items = listOf("en", "de", "tr", "uk", "az")
        lifecycleScope.launch{
            val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.language_spinner_items, R.layout.spinner_text)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
            val selected= LANGUAGE
            binding.spinner.setSelection(items.indexOf(selected))
            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedItem = items[position]
                    if (selectedItem!=selected){
                        setLocale(selectedItem)
                        viewModel.saveLanguage(selectedItem)
                        LANGUAGE=selectedItem
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        }

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

    fun setLocale(language: String) {
        val locale=Locale(language)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        findNavController().run {
            popBackStack()
            navigate(R.id.myProfileFragment)
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
                    email=response.email.toString()
                    login=response.login.toString()
                    Glide.with(requireContext())
                        .load(response.avatarUrl)
                        .error(R.drawable.user_photo)
                        .placeholder(R.drawable.user_photo)
                        .transform(CircleCrop())
                        .into(binding.userPhoto)
                }
                is Results.Error ->{
                    Log.d("Results.Error",result.exception)
                }
            }

        }
    }

    private fun initializeRecycleView() {
        val texts= mutableListOf(
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
        val adapter = MyProfileItemsAdapter(texts,icons,this)
        val layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        }

    override fun onClick() {
        val userInfo = RecoveryPasswordModel(email,login)
        val action = MyProfileFragmentDirections.actionMyProfileFragmentToGetRecoveryFragment(userInfo)
        findNavController().navigate(action)
    }


}