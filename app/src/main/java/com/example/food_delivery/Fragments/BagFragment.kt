package com.example.food_delivery.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.R
import com.example.food_delivery.Adapters.adapterBag
import com.example.food_delivery.databinding.FragmentBagBinding
import com.example.food_delivery.modals.Entity.Bag


class bagFragment : Fragment() {
    lateinit var binding: FragmentBagBinding
    lateinit var data : List<Bag>
    private lateinit var adapter: adapterBag
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = FragmentBagBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val args = arguments
        data = loadData(args?.getInt("rest")!!);
        adapter = adapterBag(requireActivity(),data,::deleteItem)
        binding.recyclerView.adapter = adapter
        binding.valid.setOnClickListener {
            val db = AppDatabase.buildDatabase(requireActivity());
            db?.getBagDao()?.deleteByRest(args?.getInt("rest")!!);
            it.findNavController().navigate(R.id.action_bagFragment_to_profileFragment);

        }



        val root = binding.root
        return root
    }

    fun loadData(rest : Int):List<Bag> {
        var data : List<Bag> = listOf();
        try {
            val db = AppDatabase.buildDatabase(requireActivity());
            data = db?.getBagDao()?.getAllBags(rest)!!
            println(data);
            return data
        }catch (err : java.lang.Error){
            println("error")
            return  data
        }
    }
    fun deleteItem(position: Int) {
        // Remove the item from the data source
        data = data.filterIndexed { index, _ -> index != position }
        adapter.notifyItemRangeRemoved(position,data.size)


    }

}