package com.example.food_delivery.ViewModal

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.Utils.DataType.MenuData
import com.example.food_delivery.Utils.DataType.MenusData
import com.example.food_delivery.modals.Entity.Bag

class BagModal: ViewModel() {
    val bags = MutableLiveData<List<Bag>>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun loadBags(context: Context , rest : String) {
        try {
                loading.value = true
                val db = AppDatabase.buildDatabase(context);
                bags.value  = db?.getBagDao()?.getAllBags(rest)!!
                loading.value = false
        }catch  (err : java.lang.Error) {
            errorMessage.value = "Une erreur s'est produite"
        }
    }

    fun deleteBags(context: Context,bag : Bag,) {
        try {
            val db = AppDatabase.buildDatabase(context);
            db?.getBagDao()?.delete(bag)!!
            bags.value  = db?.getBagDao()?.getAllBags(bag.rest!!)!!
        }catch  (err : java.lang.Error) {
            errorMessage.value = "Une erreur s'est produite"
        }
    }

    fun addToBag(context: Context,data: MenuData,qty : Int){
        val db = AppDatabase.buildDatabase(context);
        if (db?.getBagDao()?.getBagByName(data?.name)?.size == 0) {
            val bag = Bag(id = data?._id!!, name = data?.name,price = data?.avg.toString().toFloat(), logoUrl =  data?.logoUrl,descr =  data?.desc,qty = qty,rest = data?.rest )
            db?.getBagDao()?.addBag(bag);
        }else {
            var bag = db?.getBagDao()?.getBagByName(data?.name)?.get(0)
            bag?.qty = bag?.qty?.plus(qty)
            db?.getBagDao()?.updateBag(bag!!)
        }
    }

}