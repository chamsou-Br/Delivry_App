package com.example.food_delivery.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_delivery.Adapters.adapterRestaurants
import com.example.food_delivery.AuthActivity
import com.example.food_delivery.R
import com.example.food_delivery.Utils.DataType.RestaurantsData
import com.example.food_delivery.ViewModal.ClientModal
import com.example.food_delivery.databinding.FragmentMainBinding
import com.example.movieexample.viewmodel.RestaurantModal
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.android.gms.tasks.OnCompleteListener


class restaurantFragment : Fragment() {
    var  data = mutableListOf<RestaurantsData>()
    lateinit var binding: FragmentMainBinding
    lateinit var RestModal: RestaurantModal
    lateinit var clientMod : ClientModal



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentMainBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        RestModal = ViewModelProvider(requireActivity()).get(RestaurantModal::class.java)
        clientMod = ViewModelProvider(requireActivity())[ClientModal::class.java]
        val adapter = adapterRestaurants(requireActivity())
        binding.recyclerView.adapter = adapter
        RestModal.loadRests()

        RestModal.restaurants.observe(requireActivity()) { rests ->
                adapter.setRestaurants(rests!!)
        }
        // loading observer
        RestModal.loading.observe(requireActivity(), { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        RestModal.errorMessage.observe(requireActivity(), { errorMessaage ->
            Toast.makeText(requireContext(), errorMessaage, Toast.LENGTH_SHORT).show()
        })

        binding.logOut.setOnClickListener {
            // signi with google
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .build()
            val client = GoogleSignIn.getClient(requireContext(), gso);
            client.signOut()
            val pref = requireActivity().getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
            if (pref.contains("connected")) {
                val pref2 = requireContext().getSharedPreferences("food_delivry", Context.MODE_PRIVATE).edit()
                pref2.remove("connected")
                pref2.remove("token_food_delivry")
                pref2.apply()
              //  clientMod.client.value = null  ;
                val intent = Intent(requireActivity(), AuthActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(requireActivity(), AuthActivity::class.java)
                startActivity(intent)
            }
        }




        val root = binding.root
        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        // Show the action bar when the fragment is destroyed
        requireActivity().actionBar?.hide()
    }


}