package com.example.food_delivery.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.food_delivery.MainActivity
import com.example.food_delivery.R
import com.example.food_delivery.Utils.DataType.clientData
import com.example.food_delivery.ViewModal.ClientModal
import com.example.food_delivery.databinding.FragmentLoginBinding
import com.example.food_delivery.databinding.FragmentRegisterBinding


class registerFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    lateinit var clientMod : ClientModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        binding.toLogin.setOnClickListener{
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        clientMod = ViewModelProvider(requireActivity()).get(ClientModal::class.java)
        binding.signin.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val phone = binding.phone.text.toString()
            val address = binding.address.text.toString()
            val fullName = binding.fullName.text.toString()
            val picture = "https://th.bing.com/th/id/R.af4897be5959a8e01db8f36a1f2675aa?rik=AY8fu9NGu8%2flbw&riu=http%3a%2f%2fstatesmanbiz.com%2fwp-content%2fuploads%2f2019%2f09%2fRMH-Statesman-Website-Headshot-Silhouette-300x200.jpg&ehk=qj6x%2bWrRtPcw%2bi0jMjZ4Nh3gJ1jKv6Se%2fs6IR3Y3zbk%3d&risl=&pid=ImgRaw&r=0"
            val client =  clientData(fullName = fullName,email = email,address = address , phone =  phone, password = password , picture = picture);
            clientMod.register(client)
        }
        // loading observer
        clientMod.loading.observe(requireActivity(), { loading ->
            if (loading) {
                binding.progressBar2.visibility = View.VISIBLE
                binding.signTitle.visibility = View.GONE
            } else {
                binding.progressBar2.visibility = View.GONE
                binding.signTitle.visibility = View.VISIBLE
            }
        })

        clientMod.isConnected.observe(requireActivity(),{isConnect ->
            val pref = requireActivity().getSharedPreferences("food_delivry", Context.MODE_PRIVATE).edit()
            if (isConnect== true){
                pref.putBoolean("connected",true)
                pref.putString("token_food_delivry",clientMod.client.value?.token)
                pref.apply()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
            }else{
                pref.remove("connected");
                pref.apply()
            }
        })

        clientMod.errorMessage.observe(requireActivity(), { errorMessaage ->
            Toast.makeText(requireContext(), errorMessaage, Toast.LENGTH_SHORT).show()
        })
        return binding.root
    }
}