package com.example.food_delivery.Adapters

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.Transliterator.Position
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_delivery.*
import com.example.food_delivery.Utils.DataType.RestaurantsData
import com.example.food_delivery.databinding.MovieLayoutBinding

class adapterRestaurants(val ctx : Context):RecyclerView.Adapter<adapterRestaurants.MyViewHolder>() {

    val idarray = arrayOf("com.facebook.lite","com.facebook.katana")
    var data = mutableListOf<RestaurantsData>()

    fun setRestaurants(movies: List<RestaurantsData>) {
        this.data = movies.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(MovieLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            //val intent = Intent(ctx,MainActivity2::class.java)
            val bundle = Bundle()
            bundle.putInt("rest",position)
            bundle.putString("id",data[position]._id)
            it.findNavController().navigate(R.id.action_mainFragment_to_detailsFragment,bundle)
        }
        holder.binding.apply {
            name.text = data[position].name;
            Glide.with(holder.itemView.context)
                .load(data[position].logoUrl)
                .into(logo)


        }



    }



    class MyViewHolder(val binding: MovieLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}



