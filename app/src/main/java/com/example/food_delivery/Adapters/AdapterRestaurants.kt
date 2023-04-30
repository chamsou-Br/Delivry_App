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

class adapterRestaurants(val ctx : Context, val data:List<RestaurantsData>):RecyclerView.Adapter<adapterRestaurants.MyViewHolder>() {

    val idarray = arrayOf("com.facebook.lite","com.facebook.katana")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(MovieLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            //val intent = Intent(ctx,MainActivity2::class.java)
            val bundle = Bundle()
            bundle.putInt("rest",position)
            it.findNavController().navigate(R.id.action_mainFragment_to_detailsFragment,bundle)
        }
        holder.binding.apply {
            name.text = data[position].name;
            type.text = data[position].type;
            adress.text = data[position].adress
            rating.text = data[position].avg.toString();
            Glide.with(holder.itemView.context)
                .load(data[position].logoUrl)
                .into(logo)
           // logo.setImageResource(R.drawable.res)
            review.text = data[position].review.toString() + " Review"

            fb.setOnClickListener{
                openFb(ctx,data[position].fbApp,data[position].fbUrl)
            }
            map.setOnClickListener {
                openMap(ctx,data[position].mapX,data[position].mapY)
            }
            phone.setOnClickListener{
                call(ctx,data[position].phone)
            }
            mail.setOnClickListener {
                mailTo(ctx,data[position].email)
            }



        }



    }

    /**
     * This function opens an URL
     */

    fun openPage(ctx: Context, url: String, urlWeb: String, idarray:Array<String>) {
        var isexist=false;
        for (i in 0..idarray.size-1)
        {
            if(isAppInstalled(ctx,idarray[i])){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                ctx.startActivity(intent)
                isexist=true
                break
            }
        }

        if(!isexist){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlWeb))
            ctx.startActivity(intent)
        }


    }
    fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }


    class MyViewHolder(val binding: MovieLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}



