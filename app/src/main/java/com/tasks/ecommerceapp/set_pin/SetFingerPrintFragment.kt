package com.tasks.ecommerceapp.set_pin

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.fragment.app.viewModels
import com.tasks.ecommerceapp.R

import com.tasks.ecommerceapp.databinding.FragmentStFingerprintBinding
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text


@AndroidEntryPoint
class SetFingerPrintFragment : Fragment() {

    private var _binding: FragmentStFingerprintBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SetPinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStFingerprintBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (BiometricManager.from(requireContext()).canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            viewModel.enableFingerprint(this)
            viewModel.isFingerprintEnabled.observe(viewLifecycleOwner) { isFingerprintEnabled ->
                if (isFingerprintEnabled) {
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
        dialog.show()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}