package com.example.food_delivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.databinding.ActivityMainBinding
import com.example.food_delivery.modals.Entity.User

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        actionBar?.hide()
        try {
            val db = AppDatabase.buildDatabase(this);
            val user = User(100,"chamsou","berkane","jc_berkane@esi.dz","chamsou2002");

        }catch (err : java.lang.Error){
            println("error")
        }


    }




}
