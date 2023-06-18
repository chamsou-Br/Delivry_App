package com.example.food_delivery

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.food_delivery.Utils.DataType.tokenData
import com.example.food_delivery.ViewModal.ClientModal
import com.example.food_delivery.databinding.ActivityMainBinding
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var clientModal : ClientModal


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)

        clientModal = ViewModelProvider(this).get(ClientModal::class.java)
        val pref = getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
        clientModal.getProfile(tokenData(pref.getString("token_food_delivry","")!!) )
        val fragmentIndex = intent.getIntExtra("fragmentIndex", 0)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        val navController = navHostFragment!!.navController
        if (fragmentIndex ==1){
            val pref = getSharedPreferences("food_delivry", Context.MODE_PRIVATE)
            val bundle = Bundle()
            bundle.putString("id",pref.getString("rest",null))
            navController.navigate(R.id.action_mainFragment_to_validateFragment,bundle)
        }

        NavigationUI.setupWithNavController(binding.navBottom,navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.mainFragment || destination.id == R.id.oldOrders || destination.id == R.id.profileFragment) {
              binding.navBottom.visibility = View.VISIBLE
            } else {
                binding.navBottom.visibility = View.GONE
            }
        }
        val view = binding.root
        // Obtain the registration token
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                // Handle the registration token here
                // You can send the token to your server or use it for other purposes
                Log.d(TAG, "Registration token: $token")
            } else {
                // Handle token retrieval failure
                Log.e(TAG, "Failed to retrieve registration token")
            }
        }
        setContentView(view)



    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.overflow_menw, menu)
        return true
    }

    companion object {
        private const val TAG = "MainActivity"
    }






}
