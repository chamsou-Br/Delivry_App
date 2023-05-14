package com.example.food_delivery.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.food_delivery.Adapters.adapterRestaurants
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.R
import com.example.food_delivery.Utils.DataType.MenuData
import com.example.food_delivery.databinding.FragmentDetailMenuBinding
import com.example.food_delivery.modals.Entity.Bag
import com.example.food_delivery.services.menusServiceAPI
import kotlinx.coroutines.*


class detailMenuFragment : Fragment() {

    lateinit var binding: FragmentDetailMenuBinding
     var data : MenuData? = null;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args = arguments
        val id = args?.getString("id");
        loadMenus(id!!)
        binding = FragmentDetailMenuBinding.inflate(layoutInflater)


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
                val db = AppDatabase.buildDatabase(requireActivity());
                if (db?.getBagDao()?.getBagByName(data?.name)?.size == 0) {
                    val bag = Bag(id = data?._id!!, name = data?.name,price = data?.avg.toString().toFloat(), logoUrl =  data?.logoUrl,descr =  data?.desc,qty = qty.text.toString().toInt(),rest = data?.rest )
                    db?.getBagDao()?.addBag(bag);
                }else {
                    var bag = db?.getBagDao()?.getBagByName(data?.name)?.get(0)
                    bag?.qty = bag?.qty?.plus(qty.text.toString().toInt())
                    db?.getBagDao()?.updateBag(bag!!)
                }
                val bundle = Bundle()
                bundle.putString("rest", data?.rest!!)
                it.findNavController().navigate(R.id.action_detailMenuFragment_to_bagFragment,bundle)
            }
        }

        val root = binding.root
        return root
    }

    fun loadMenus(id : String) {
    println(id)
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                println(throwable.message)
                Toast.makeText(requireActivity(), throwable.message, Toast.LENGTH_LONG).show()
            }
        }
        CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {
            val response = menusServiceAPI.createMenuServiceAPI().getMenusById(id);
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null) {
                    var body = response.body()
                    if (body != null) {
                        data = MenuData(_id = body._id ,rest = body.rest,name=body.name, logoUrl =  body.logoUrl, desc = body.desc , avg = body.avg , type = body.type , calories = body.calories , size = body.size , cooking = body.cooking).also { data = it }
                    }
                    binding.apply {
                        Glide.with(requireActivity())
                            .load(data?.logoUrl)
                            .into(img)
                        name.text = data?.name
                        type.text = data?.type
                        descr.text = data?.desc
                        price.text= "$ " + data?.avg
                        sizeValue.text = data?.size
                        caloriesValue.text = data?.calories
                        cookingValue.text = data?.cooking
                    }
                } else {
                    Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}