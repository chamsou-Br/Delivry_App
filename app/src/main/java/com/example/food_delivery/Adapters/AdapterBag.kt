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
import com.example.food_delivery.Utils.DataType.MenuData
import com.example.food_delivery.databinding.BagLayoutBinding
import com.example.food_delivery.databinding.MovieLayoutBinding
import com.example.food_delivery.modals.Entity.Bag

class adapterBag(val ctx : Context, val deleteItem: (Int) -> Unit , val addQtyToBag : (String) -> Unit , val deleteQtyFromBag : (String) -> Unit): RecyclerView.Adapter<adapterBag.MyViewHolder>() {


    var data = mutableListOf<Bag>()

    fun setBag(data: List<Bag>) {
        this.data = data.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return adapterBag.MyViewHolder(
            BagLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val db = AppDatabase.buildDatabase(ctx);
        holder.itemView.setOnClickListener {
            //val intent = Intent(ctx,MainActivity2::class.java)
            val bundle = Bundle()
            bundle.putString("id",data[position].id);
            it.findNavController().navigate(R.id.action_bagFragment_to_detailMenuFragment,bundle)
        }
       holder.binding.apply {
            name.text =   data[position].name;
            qty.text = data[position].qty.toString()
            price.text = "$ " +  (data[position].price!! * data[position].qty!!.toFloat() ).toString()
            Glide.with(holder.itemView.context)
                .load(data[position].logoUrl)
                .into(logo)
            delete.setOnClickListener {
                deleteItem(position)
            }
           plusContainer.setOnClickListener {
               qty.text = (qty.text.toString().toInt() + 1).toString()
               addQtyToBag(data[position].name!!)
           }
           minusContainer.setOnClickListener {
               if (qty.text.toString().toInt() > 1) {
                   qty.text = (qty.text.toString().toInt() - 1).toString()
                   deleteQtyFromBag(data[position].name!!)
               }
           }
        }


    }


    class MyViewHolder(val binding: BagLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}