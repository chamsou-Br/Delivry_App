package com.example.food_delivery.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.food_delivery.R
import com.example.food_delivery.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        binding.go.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_mainFragment);
        }
        binding.logout.setOnClickListener {
            val pref = requireContext().getSharedPreferences("fileName", Context.MODE_PRIVATE).edit()
            pref.remove("connected")
            pref.apply()
            it.findNavController().navigate(R.id.action_profileFragment_to_loginFragment);
        }
        return binding.root
    }

}