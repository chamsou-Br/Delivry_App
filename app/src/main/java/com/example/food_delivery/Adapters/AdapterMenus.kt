package com.example.food_delivery.Adapters


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_delivery.*
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.Utils.DataType.MenuData
import com.example.food_delivery.Utils.DataType.MenusData
import com.example.food_delivery.Utils.DataType.RestaurantsData
import com.example.food_delivery.databinding.BagLayoutBinding
import com.example.food_delivery.databinding.MenuLayoutBinding
import com.example.food_delivery.modals.Entity.Bag

class adapterMenus(val ctx : Context):RecyclerView.Adapter<adapterMenus.MyViewHolder>() {

    var data = mutableListOf<MenuData>()

    fun setMenus(data: List<MenuData>) {
        this.data = data.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  adapterMenus.MyViewHolder(
            MenuLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return MyViewHolder(MenuLayoutBinding.inflate(LayoutInflater.from(parent.context)) )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id",data[position]._id);
            it.findNavController().navigate(R.id.action_detailsFragment_to_detailMenuFragment,bundle)
        }

        holder.binding.apply {
            name.text =   data[position].name;
            price.text = "$ " + data[position].avg
            typeMenu.text = data[position].type
            Glide.with(holder.itemView.context)
                .load(data[position].logoUrl)
                .into(logo)

        }

    }


    class MyViewHolder(val binding: MenuLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}



