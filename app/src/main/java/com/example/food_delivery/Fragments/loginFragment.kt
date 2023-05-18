package com.example.food_delivery.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.food_delivery.R
import com.example.food_delivery.Utils.DataType.clientData
import com.example.food_delivery.ViewModal.ClientModal
import com.example.food_delivery.databinding.FragmentLoginBinding
import com.example.food_delivery.databinding.FragmentProfileBinding
import com.example.movieexample.viewmodel.RestaurantModal


class loginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var clientMod : ClientModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        clientMod = ViewModelProvider(requireActivity()).get(ClientModal::class.java)
        binding.toRegister.setOnClickListener{
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.signin.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val client =  clientData(email = email, password = password);
            clientMod.login(client)
        }
        // loading observer
        clientMod.loading.observe(requireActivity(), { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.signTitle.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.signTitle.visibility = View.VISIBLE
            }
        })

        clientMod.errorMessage.observe(requireActivity(), { errorMessaage ->
            Toast.makeText(requireContext(), errorMessaage, Toast.LENGTH_SHORT).show()
        })


        return binding.root
    }


}