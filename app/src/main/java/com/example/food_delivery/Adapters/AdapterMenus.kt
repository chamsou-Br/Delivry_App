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
import com.example.food_delivery.Utils.DataType.MenusData
import com.example.food_delivery.databinding.MenuLayoutBinding
import com.example.food_delivery.modals.Entity.Bag

class adapterMenus(val ctx : Context,val data:List<MenusData>):RecyclerView.Adapter<adapterMenus.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(MenuLayoutBinding.inflate(LayoutInflater.from(parent.context)) )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val nameMenu =  data[position].name
        val avg = data[position].avg
        val logoUrl = data[position].logoUrl
        val rest = data[position].rest
        val desc = data[position].desc

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", position)
            bundle.putString("logoUrl" ,logoUrl)
            bundle.putString("name" ,nameMenu)
            bundle.putFloat("avg" ,avg)
            bundle.putInt("rest",rest)
            it.findNavController().navigate(R.id.action_detailsFragment_to_detailMenuFragment,bundle)
        }

        holder.binding.apply {
            name.text =   nameMenu;
            descr.text = desc
            price.text = avg.toString()
            Glide.with(holder.itemView.context)
                .load(logoUrl)
                .into(logo)
            //logo.setImageResource(R.drawable.foo)
            addToBag.setOnClickListener {
                val db = AppDatabase.buildDatabase(ctx);
                if (db?.getBagDao()?.getBagByName(nameMenu)?.size == 0) {
                    val bag = Bag(name = nameMenu,price = avg, logoUrl =  logoUrl,descr =  desc,qty = 1,rest = rest)
                    db?.getBagDao()?.addBag(bag);
                }else {
                    var bag = db?.getBagDao()?.getBagByName(nameMenu)?.get(0)
                    bag?.qty = bag?.qty?.plus(1)
                    db?.getBagDao()?.updateBag(bag!!)
                }
                val message = "commades is added to bag *_*" // the message to display
                val duration = Toast.LENGTH_SHORT // the duration of the message (short or long)
                val toast = Toast.makeText(ctx, message, duration)
                toast.show()
            }
        }
    }


    class MyViewHolder(val binding: MenuLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}


