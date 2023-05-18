package com.example.food_delivery.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.food_delivery.Adapters.adapterRestaurants
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.R
import com.example.food_delivery.Utils.DataType.MenuData
import com.example.food_delivery.ViewModal.BagModal
import com.example.food_delivery.ViewModal.MenuModal
import com.example.food_delivery.databinding.FragmentDetailMenuBinding
import com.example.food_delivery.modals.Entity.Bag
import com.example.food_delivery.services.menusServiceAPI
import kotlinx.coroutines.*


class detailMenuFragment : Fragment() {

    lateinit var binding: FragmentDetailMenuBinding
    lateinit var menuModal: MenuModal
     var data : MenuData? = null;
    lateinit var bagModal: BagModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailMenuBinding.inflate(layoutInflater)
        val root = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val id = args?.getString("id");
        menuModal = ViewModelProvider(requireActivity()).get(MenuModal::class.java)
        bagModal = ViewModelProvider(requireActivity()).get(BagModal::class.java)
        println(id!! + " data")
        menuModal.loadMenu(id!!)
        menuModal.menu.observe(requireActivity()) { data ->
            println(data)
            this.data = data;
            binding.apply {
                if (isAdded){
                    Glide.with(requireActivity())
                        .load(data?.logoUrl)
                        .into(img)
                }
                name.text = data?.name
                type.text = data?.type
                descr.text = data?.desc
                price.text= "$ " + data?.avg
                sizeValue.text = data?.size
                caloriesValue.text = data?.calories
                cookingValue.text = data?.cooking
            }
        }

        menuModal.loading.observe(requireActivity(), { loading ->
            if (loading) {
                binding.progressBarDetails.visibility = View.VISIBLE
                binding.view.visibility = View.GONE
            } else {
                binding.progressBarDetails.visibility = View.GONE
                binding.view.visibility = View.VISIBLE
            }
        })

        menuModal.errorMessage.observe(requireActivity(), { errorMessaage ->
            Toast.makeText(requireContext(), errorMessaage, Toast.LENGTH_SHORT).show()
        })

        binding.apply {
            navigateUp.setOnClickListener {
                it.findNavController().navigateUp()
            }
            plusContainer.setOnClickListener {
                qty.text = (qty.text.toString().toInt() + 1).toString()
            }
            minusContainer.setOnClickListener {
                if (qty.text.toString().toInt() > 1) {
                    qty.text = (qty.text.toString().toInt() - 1).toString()
                }
            }
            bag.setOnClickListener {
                val pref = requireActivity().getSharedPreferences("fileName", Context.MODE_PRIVATE)
                if (pref.contains("connected")) {
                    // The "connected" preference value exists$
                }
                val bundle = Bundle()
                bundle.putString("rest", data?.rest)
                it.findNavController().navigate(R.id.action_detailMenuFragment_to_bagFragment,bundle)
            }
            addToBag.setOnClickListener {
                bagModal.addToBag(requireActivity(),data!!,qty.text.toString().toInt())
                val bundle = Bundle()
                bundle.putString("rest", data?.rest!!)
                it.findNavController().navigate(R.id.action_detailMenuFragment_to_bagFragment,bundle)
            }
        }

    }


}