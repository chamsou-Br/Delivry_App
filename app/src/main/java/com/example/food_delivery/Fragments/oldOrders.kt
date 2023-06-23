package com.example.food_delivery.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_delivery.Adapters.adapterRestaurants
import com.example.food_delivery.R
import com.example.food_delivery.Utils.DataType.RestaurantsData
import com.example.food_delivery.databinding.FragmentMainBinding
import com.example.food_delivery.databinding.FragmentOldOrdersBinding
import com.example.movieexample.viewmodel.RestaurantModal


class oldOrders : Fragment() {
    lateinit var binding: FragmentOldOrdersBinding
    var  data = mutableListOf<RestaurantsData>()
    lateinit var RestModal: RestaurantModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOldOrdersBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        RestModal = ViewModelProvider(requireActivity()).get(RestaurantModal::class.java)
        val adapter = adapterRestaurants(requireActivity())
        binding.recyclerView.adapter = adapter
        RestModal.searchRest("")

        RestModal.restaurantsSearch.observe(requireActivity()) { rests ->
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

        binding.send.setOnClickListener {
            RestModal.searchRest(binding.search.text.toString())
        }


        val root = binding.root
        return root
    }


}