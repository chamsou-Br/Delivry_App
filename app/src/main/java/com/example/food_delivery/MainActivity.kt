package com.example.food_delivery

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.food_delivery.Fragments.bagFragment
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.databinding.ActivityMainBinding
import com.example.food_delivery.modals.Entity.User


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)

        try {
            val db = AppDatabase.buildDatabase(this);
            val user = User(100,"chamsou","berkane","jc_berkane@esi.dz","chamsou2002");

        }catch (err : java.lang.Error){
            println("error")
        }
        // Retrieve the fragment index from the intent

        val fragmentIndex = intent.getIntExtra("fragmentIndex", 0)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        val navController = navHostFragment!!.navController
        if (fragmentIndex ==1){
            val pref = getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
            val bundle = Bundle()
            bundle.putString("id",pref.getString("rest",null))
            println("main")
            println(pref.getString("rest",null))
            navController.navigate(R.id.action_mainFragment_to_validateFragment,bundle)
        }

        val view = binding.root
        setContentView(view)



    }




}
