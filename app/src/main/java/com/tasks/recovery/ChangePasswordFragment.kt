package com.tasks.recovery

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentChangePasswordBinding
import com.tasks.ecommerceapp.registration.CustomerViewModel
import com.tasks.ecommerceapp.utils.CheckViewsValid
import com.tasks.ecommerceapp.utils.DataStoreManager
import com.tasks.ecommerceapp.utils.Results
import com.tasks.ecommerceapp.utils.datastore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChangePasswordFragment:Fragment() {
    private var _binding: FragmentChangePasswordBinding?=null
    private val binding get()=_binding
    private val viewModel: CustomerViewModel by activityViewModels()
    private var newPassword=""
    private var dublicatePassword=""
    private val checksViewValids: CheckViewsValid by lazy {
        CheckViewsValid(requireContext())
    }

    private lateinit var dataStoreManager: DataStoreManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentChangePasswordBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.newPassword?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(passwordText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                newPassword=passwordText.toString()
                checkEmailorPasswordisValid()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding?.dublicatePassword?.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(passwordText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                dublicatePassword=passwordText.toString()
                checkEmailorPasswordisValid()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding?.change?.setOnClickListener {
            newPassword=binding?.newPassword?.text.toString()
            dublicatePassword=binding?.dublicatePassword?.text.toString()
            changePassword(newPassword,dublicatePassword)
        }

    }

    private fun changePassword(newPassword: String, dublicatePassword: String) {
        if (newPassword==dublicatePassword){
            dataStoreManager= DataStoreManager(requireContext().datastore)
                Log.d("LOG",""+viewModel.password.toByteArray())


            val credentials = "${viewModel.toEmail}:${viewModel.password}"
            val base64 = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
            val authorization = "Basic $base64"
            Log.d("LOG+",""+authorization)
            viewModel.changePassword(authorization,viewModel.password,newPassword)
            viewModel.changePasswordResult.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Results.Success -> {
                        showDialog()
                    }
                    is Results.Error -> {
                        Log.d("LOG",""+result.exception)
                    }
                }
            }
        }else
        {
            Toast.makeText(requireContext(),getString(R.string.passwords_dont_match),Toast.LENGTH_LONG).show()
        }

    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_alert, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

    }

    private fun checkEmailorPasswordisValid() {
        if (newPassword.length<7 || dublicatePassword.length<7){
               checksViewValids.notEnabled(binding?.change)
        } else{
                checksViewValids.setEnabled(binding?.change)
    }
    }


}