package com.example.food_delivery.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.food_delivery.R
import com.example.food_delivery.databinding.FragmentLoginBinding


class loginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(layoutInflater);

          binding.sign.setOnClickListener {

                val email = binding.editText.text.toString() ;
                val password = binding.editText2.text.toString()
                 val args = arguments
                if (email == "test@gmail.com" && password == "test") {
                    val pref = requireActivity().getSharedPreferences("fileName",Context.MODE_PRIVATE).edit()
                    pref.putBoolean("connected",true)
                    pref.apply()
                    val bundle = Bundle()
                    bundle.putInt("rest", args?.getInt("rest")!!)
                    findNavController().navigate(R.id.action_loginFragment_to_bagFragment2,bundle)

                }else {
                    println("kndflekrng")
                    binding.error.setText("password/email incorrect")
                }
            }
        return binding.root
    }


}