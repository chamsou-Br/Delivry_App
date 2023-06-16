package com.example.food_delivery.Fragments

import android.content.Context
import android.content.Intent
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
import com.example.food_delivery.AuthActivity
import com.example.food_delivery.ViewModal.BagModal
import com.example.food_delivery.ViewModal.ClientModal
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
        adapter =  adapterBag(requireActivity(),::deleteItem,::addQtyToBag , ::deleteQtyFromBag)
        binding.recyclerView.adapter = adapter
        bagModal = ViewModelProvider(requireActivity()).get(BagModal::class.java)
        bagModal.loadBags(requireActivity(),args?.getString("rest")!!)
        bagModal.bags.observe(requireActivity()) { bags ->
            adapter.setBag(bags)
            var price = 0.0;
            bagModal.bags.value?.forEach { bag ->
                price += bag.price!! * bag.qty!!
            }
            binding.subTotalValue.text = "$ " + price.toString();
            binding.priceTotalValue.text = "$ " + price.toString();
        }
        binding.apply {
            navigateUp.setOnClickListener {
                it.findNavController().navigateUp()
            }
            valid.setOnClickListener {
                val pref = requireActivity().getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
                if (pref.contains("connected")) {
                    val bundle = Bundle()
                    bundle.putString("id",args?.getString("rest")!!)
                    it.findNavController().navigate(R.id.action_bagFragment_to_validateFragment,bundle);
                } else {
                    val pref2 = requireActivity().getSharedPreferences("food_delivry", Context.MODE_PRIVATE).edit()
                    pref2.putInt("fragmentIndex",1);
                    pref2.apply();
                    val intent = Intent(requireActivity(), AuthActivity::class.java)
                    startActivity(intent)
                }
            }
        }


        val root = binding.root
        return root
    }


    fun deleteItem(position: Int) {

        bagModal.deleteBags(requireActivity(), bagModal.bags.value!![position])
    }

    fun addQtyToBag(name : String){
        bagModal.addQtyToBag(requireActivity(),name)
    }
    fun deleteQtyFromBag(name : String){
        bagModal.deleteQtyFromBag(requireActivity() , name);
    }

}