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
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.R
import com.example.food_delivery.databinding.FragmentDetailMenuBinding
import com.example.food_delivery.modals.Entity.Bag


class detailMenuFragment : Fragment() {

    lateinit var binding: FragmentDetailMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailMenuBinding.inflate(layoutInflater)
        binding.apply {
            val args = arguments
            val position = args?.getInt("id")
            val logoUrl = args?.getString("logoUrl")
            val avg = args?.getFloat("avg")
            val nameMenu = args?.getString("name")
            val rest = args?.getInt("rest");
            val desc = "Ce soir, c’est menu mexicain ? Pas question de faire l’impasse sur les tacos, stars de la cuisine mexicaine récupérées par sa cousine tex mex. Faciles à faire et encore plus faciles à dévorer, ces tortillas de maïs pliées ou roulées sont généreusement garnies"
            //img.setImageResource(R.drawable.foo)
            Glide.with(requireActivity())
                .load(logoUrl)
                .into(img)
            name.text = nameMenu
            descr.text = desc
            price.text= "$ " + avg.toString()
            bag.setOnClickListener {
                val db = AppDatabase.buildDatabase(requireActivity());
                if (db?.getBagDao()?.getBagByName(nameMenu)?.size == 0) {
                    val bag = Bag(name = nameMenu,price = avg, logoUrl =  logoUrl,descr =  desc,qty = qty.text.toString().toInt(),rest = rest)
                    db?.getBagDao()?.addBag(bag);
                }else {
                    var bag = db?.getBagDao()?.getBagByName(nameMenu)?.get(0)
                    bag?.qty = bag?.qty?.plus(qty.text.toString().toInt())
                    db?.getBagDao()?.updateBag(bag!!)
                }
                val message = "commades is added to bag *_*" // the message to display
                val duration = Toast.LENGTH_SHORT // the duration of the message (short or long)
                val toast = Toast.makeText(context, message, duration)
                toast.show()
            }
            checkout.setOnClickListener {
                val pref = requireActivity().getSharedPreferences("fileName", Context.MODE_PRIVATE)
                val bundle = Bundle()
                bundle.putInt("rest", rest!!)
                if (pref.contains("connected")) {
                    // The "connected" preference value exists$
                    it.findNavController().navigate(R.id.action_detailMenuFragment_to_bagFragment,bundle)
                } else {
                    // The "connected" preference value does not exist
                    it.findNavController().navigate(R.id.action_detailMenuFragment_to_loginFragment,bundle)
                }

            }

        }

        val root = binding.root
        return root
    }

}