package com.example.food_delivery.Adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.R
import com.example.food_delivery.databinding.BagLayoutBinding
import com.example.food_delivery.modals.Entity.Bag

class adapterBag(val ctx : Context, val data:List<Bag>, val deleteItem: (Int) -> Unit): RecyclerView.Adapter<adapterBag.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(BagLayoutBinding.inflate(LayoutInflater.from(parent.context)) )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            //val intent = Intent(ctx,MainActivity2::class.java)
            val bundle = Bundle()
            bundle.putInt("id", position)
            bundle.putString("logoUrl" ,data[position].logoUrl)
            bundle.putString("name" ,data[position].name)
            bundle.putFloat("avg" , data[position].price!!)
            it.findNavController().navigate(R.id.action_bagFragment_to_detailMenuFragment,bundle)
        }

        holder.binding.apply {
            name.text =   data[position].name;
            qty.text = data[position].qty.toString() + " qty"
            price.text = "$ " +  data[position].price.toString()
            Glide.with(holder.itemView.context)
                .load(data[position].logoUrl)
                .into(logo)
            delete.setOnClickListener {
                val db = AppDatabase.buildDatabase(holder.itemView.context);
                db?.getBagDao()?.delete(data[position])
                holder.itemView.visibility = View.INVISIBLE
                deleteItem(position)
            }
        }
    }


    class MyViewHolder(val binding: BagLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}