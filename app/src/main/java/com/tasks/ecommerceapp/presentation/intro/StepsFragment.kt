package com.tasks.ecommerceapp.presentation.intro


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.databinding.StepsFragmentBinding
import com.tasks.ecommerceapp.presentation.registration.ActivityCustomerViewModel

class StepsFragment: Fragment() {
    private var _binding:StepsFragmentBinding?=null
    private val binding get() = _binding!!
    private val viewModel:ActivityCustomerViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=StepsFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var k=0
        binding.mnext.setOnClickListener {
            k+=1
            if (k==1){
                binding.step1.visibility=View.GONE
                binding.step2.visibility=View.VISIBLE
            }

            if (k==2){
                binding.mnext.text = getString(R.string.get_started)
                binding.step2.visibility=View.GONE
                binding.step3.visibility=View.VISIBLE
            }
            if (k==3){
                viewModel.saveNotFistTime()
                findNavController().navigate(R.id.action_stepsFragment_to_registration)
            }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}