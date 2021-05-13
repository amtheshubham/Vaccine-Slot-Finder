package com.example.vaccineslotfinder

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shubhaminflow.imagesearchapp.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()

    private val TAG= "edittextttttttt"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val simpleDateFormat= SimpleDateFormat("dd-MM-yyyy")
    val currentDT: String = simpleDateFormat.format(Date())
    var content: String?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        val edittextpin= binding.editTextPincode
        val successtick = binding.successtick

        binding.notifyButton.setOnClickListener {
            if(edittextpin.text.toString().trim().length==0){
                Toast.makeText(context,"Please enter a valid pincode",Toast.LENGTH_SHORT).show()
            }
            else{
                notifyButton.isVisible=false
                notifyButton.isEnabled=false
                successtick.isVisible=true
                Log.i(TAG,"110086")
                val workrequest = viewModel.runPeriodicRequest(requireContext(), edittextpin.text.toString())
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}