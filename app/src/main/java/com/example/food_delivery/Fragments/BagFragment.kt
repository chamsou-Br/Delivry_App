package com.example.food_delivery.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.R
import com.example.food_delivery.Adapters.adapterBag
import com.example.food_delivery.ViewModal.BagModal
import com.example.food_delivery.databinding.FragmentBagBinding
import com.example.food_delivery.modals.Entity.Bag
import com.example.movieexample.viewmodel.RestaurantModal


class bagFragment : Fragment() {
    lateinit var binding: FragmentBagBinding
    //lateinit var data : List<Bag>
    private lateinit var adapter: adapterBag
    lateinit var bagModal: BagModal
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = FragmentBagBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val args = arguments

        //data = loadData(args?.getString("rest")!!);
        adapter =  adapterBag(requireActivity(),::deleteItem)
        binding.recyclerView.adapter = adapter
        bagModal = ViewModelProvider(requireActivity()).get(BagModal::class.java)
        bagModal.loadBags(requireActivity(),args?.getString("rest")!!)
        bagModal.bags.observe(requireActivity()) { bags ->
            adapter.setBag(bags)
        }
        binding.apply {
            navigateUp.setOnClickListener {
                it.findNavController().navigateUp()
            }
            valid.setOnClickListener {
                val db = AppDatabase.buildDatabase(requireActivity());
                db?.getBagDao()?.deleteByRest(args?.getInt("rest")!!);
                it.findNavController().navigate(R.id.action_bagFragment_to_profileFragment);

            }
        }


        val root = binding.root
        return root
    }


    fun deleteItem(position: Int) {

        bagModal.deleteBags(requireActivity(), bagModal.bags.value!![position])
    }

}