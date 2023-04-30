package com.example.food_delivery.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_delivery.Utils.DataType.RestaurantsData
import com.example.food_delivery.Adapters.adapterRestaurants
import com.example.food_delivery.databinding.FragmentMainBinding


class restaurantFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    fun loadData():List<RestaurantsData> {
        val data = mutableListOf<RestaurantsData>()
        val rest1 = RestaurantsData("Sultan" , "https://th.bing.com/th/id/OIP.AE1KkwRv8aejxwCCACIokwHaEo?w=272&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7","BebZ" , "20" , "20" , "Algeria" , "4.6".toFloat() , 30 ,"+213664827074" ,"jc_berkane@esi.dz","fb://page/218641444910278","https://www.facebook.com/RenaultAlgerie/","fb://page/218641444910278","https://www.facebook.com/RenaultAlgerie/")
        val rest2 = RestaurantsData("House burger" , "https://th.bing.com/th/id/OIP.cbc4hJD5T0fEkl-lAgSg8gHaE8?w=283&h=189&c=7&r=0&o=5&dpr=1.1&pid=1.7", "Alger" , "20" , "20" , "Algeria" , "4.6".toFloat() , 30 ,"+213664827074" ,"jc_berkane@esi.dz","fb://page/218641444910278","https://www.facebook.com/RenaultAlgerie/","fb://page/218641444910278","https://www.facebook.com/RenaultAlgerie/")
        val rest3 = RestaurantsData("Pizza Milano" , "https://th.bing.com/th/id/OIP.gXUsMI1hAzdMOhadpGIx9AHaE9?w=252&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7", "Alger" , "20" , "20" , "Algeria" , "4.6".toFloat() , 30 ,"+213664827074" ,"jc_berkane@esi.dz","fb://page/218641444910278","https://www.facebook.com/RenaultAlgerie/","fb://page/218641444910278","https://www.facebook.com/RenaultAlgerie/")
        val rest4 = RestaurantsData("Paris Food" , "https://th.bing.com/th/id/OIP.AE1KkwRv8aejxwCCACIokwHaEo?w=272&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7", "Alger" , "20" , "20" , "Algeria" , "4.6".toFloat() , 30 ,"+213664827074" ,"jc_berkane@esi.dz","fb://page/218641444910278","https://www.facebook.com/RenaultAlgerie/","fb://page/218641444910278","https://www.facebook.com/RenaultAlgerie/")
        val rest5 = RestaurantsData("Dazdouz" , "https://th.bing.com/th/id/OIP.sjhYIsGkQr6zjJi8Rh5HogHaE6?w=284&h=189&c=7&r=0&o=5&dpr=1.1&pid=1.7", "Alger" , "20" , "20" , "Algeria" , "4.6".toFloat() , 30 ,"+213664827074" ,"jc_berkane@esi.dz","fb://page/218641444910278","https://www.facebook.com/RenaultAlgerie/","fb://page/218641444910278","https://www.facebook.com/RenaultAlgerie/")

        data.add(rest1)
        data.add(rest2)
        data.add(rest3)
        data.add(rest4)
        data.add(rest5)
        data.add(rest1)

        return data
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapterRestaurants(requireActivity(),loadData())
        val root = binding.root
        return root
    }


}