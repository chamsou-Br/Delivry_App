package com.example.food_delivery.Fragments

import com.example.food_delivery.Adapters.adapterMenus
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.food_delivery.*
import com.example.food_delivery.Adapters.adapterRestaurants
import com.example.food_delivery.Utils.DataType.MenuData
import com.example.food_delivery.Utils.DataType.MenusData
import com.example.food_delivery.Utils.DataType.RestaurantsData
import com.example.food_delivery.ViewModal.MenuModal
import com.example.food_delivery.databinding.FragmentDetailsBinding
import com.example.food_delivery.services.menusServiceAPI
import com.example.food_delivery.services.restaurantServiceAPI
import com.example.movieexample.viewmodel.RestaurantModal
import kotlinx.coroutines.*


class menuFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding
    lateinit var menuModal: MenuModal
    lateinit var restModal : RestaurantModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        val root = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        menuModal = ViewModelProvider(requireActivity()).get(MenuModal::class.java)
        restModal = ViewModelProvider(requireActivity()).get(RestaurantModal::class.java)
        val args = arguments;
        val adapter = adapterMenus(requireActivity())
        binding.recyclerView.adapter = adapter
        //loadRest(args?.getString("id")!!)
        restModal.loadRest(args?.getString("id")!!)
        restModal.restaurant.observe(requireActivity()) { rest ->
            binding.apply {

                Glide.with(requireActivity())
                    .load(rest.logoUrl)
                    .into(imgRest)
                nameRest.text = rest.name
            }
        }

        menuModal.loadMenus(args?.getString("id")!!)
        menuModal.menus.observe(requireActivity()) { menus ->
            adapter.setMenus(menus)
        }
        // loading observer
        menuModal.loading.observe(requireActivity()) { loading ->
            if (loading) {
                binding.progressBarMenus.visibility = View.VISIBLE
            } else {
                binding.progressBarMenus.visibility = View.GONE
            }
        }


        menuModal.errorMessage.observe(requireActivity()) { errorMessaage ->
            Toast.makeText(requireContext(), errorMessaage, Toast.LENGTH_SHORT).show()
        }



        binding.apply {
            back.setOnClickListener {
                it.findNavController().navigateUp()
            }
            fb.setOnClickListener {
                openFb(requireActivity(), restModal.restaurant.value!!.fbUrl, restModal.restaurant.value!!.fbUrl!!)
            }
            map.setOnClickListener {
                openMap(requireActivity(), restModal.restaurant.value!!.mapX!!, restModal.restaurant.value!!.mapY!!)
            }
            insta.setOnClickListener {
                openInsta(requireActivity(), restModal.restaurant.value!!.instApp!!,restModal.restaurant.value!!.instUrl!!)
            }
            phone.setOnClickListener {
                call(requireActivity(), restModal.restaurant.value!!.phone!!)
            }
            mail.setOnClickListener {
                mailTo(requireActivity(),restModal.restaurant.value!!.email!!)
            }
        }
    }







}