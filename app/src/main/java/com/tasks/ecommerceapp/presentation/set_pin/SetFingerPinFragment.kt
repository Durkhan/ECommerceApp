package com.tasks.ecommerceapp.presentation.set_pin

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.biometric.BiometricManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.FragmentStFingerpinBinding
import com.tasks.ecommerceapp.presentation.base.ProductsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SetFingerPinFragment : Fragment() {

    private var _binding: FragmentStFingerpinBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SetFingerPinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStFingerpinBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (BiometricManager.from(requireContext()).canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            viewModel.enableFingerprint(this)
            viewModel.isFingerprintEnabled.observe(viewLifecycleOwner) { isFingerprintEnabled ->
                if (isFingerprintEnabled) {
                      binding.mconfirm.isEnabled=false
                      showDialog()
                } else {
                    Toast.makeText(requireContext(), getString(R.string.fingerprint_authentication_failed), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            Toast.makeText(requireContext(), getString(R.string.fingerprint_authentication_not_supported), Toast.LENGTH_SHORT).show()
        }
        binding.mconfirm.setOnClickListener {
            viewModel.enableFingerprint(this)
        }
        binding

        skip()

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
    }

    private fun skip() {
        binding.skip.setOnClickListener {
            startProductsActivity()
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_alert, null)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.succesful_pin_set)
        view.findViewById<TextView>(R.id.description).text = getString(R.string.succesful_set_pin_desc)
        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        lifecycleScope.launch{
            dialog.show()
            delay(3000)
            startProductsActivity()
        }


    }

    private fun startProductsActivity() {
        requireActivity().startActivity(
            Intent(
                requireActivity(),
                ProductsActivity::class.java
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}