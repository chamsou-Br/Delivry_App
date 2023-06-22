package com.example.food_delivery.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.food_delivery.R
import com.example.food_delivery.databinding.FragmentFinBinding
import com.example.food_delivery.databinding.FragmentLoginBinding




class FinFragment : Fragment() {

    lateinit var binding: FragmentFinBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        binding.signin.setOnClickListener {
            it.findNavController().navigate(R.id.action_finFragment_to_mainFragment);
        }
        return binding.root
    }

}