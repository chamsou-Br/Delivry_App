package com.example.food_delivery.modals.test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.modals.Entity.Bag
import com.example.food_delivery.modals.Entity.User
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


class BagTest {
    private lateinit var db: AppDatabase
    private lateinit var bag : Bag;
    @Before
    fun initDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }
    @Test
    fun testInsertAndGetBag() {
        bag = Bag(id=99,name = "Frite",price = 20.0.toFloat(), logoUrl =  "logo",descr =  "desc",qty = 1,rest = 99)
        db?.getBagDao()?.addBag(bag)
        val list = db?.getBagDao()?.getBagById(99)
        assertEquals(bag,list?.get(0))
    }

    @Test
    fun testDeleteBag() {
        bag = Bag(id=98,name = "Frite",price = 20.0.toFloat(), logoUrl =  "logo",descr =  "desc",qty = 1,rest = 99)
        db?.getBagDao()?.addBag(bag)
        db?.getBagDao()?.delete(bag);
        val list = db?.getBagDao()?.getAllBags();
        assertEquals(0,list?.size)
    }

    @Test
    fun testDeleteByRst() {
        bag = Bag(id=96,name = "Frite",price = 20.0.toFloat(), logoUrl =  "logo",descr =  "desc",qty = 1,rest = 99)
        db?.getBagDao()?.addBag(bag)
        bag = Bag(id=95,name = "Frite",price = 20.0.toFloat(), logoUrl =  "logo",descr =  "desc",qty = 1,rest = 99)
        db?.getBagDao()?.addBag(bag)
        db?.getBagDao()?.deleteByRest(bag.rest!!);
        val list = db?.getBagDao()?.getAllBags();
        assertEquals(0,list?.size)
    }

    @Test
    fun testgetByRest() {
        bag = Bag(id=97,name = "Frite",price = 20.0.toFloat(), logoUrl =  "logo",descr =  "desc",qty = 1,rest = 99)
        db?.getBagDao()?.addBag(bag)
        val list = db?.getBagDao()?.getAllBags(bag.rest!!)
        assertEquals(bag.rest,list?.get(0)?.rest)
    }

    @Test
    fun testUpdateBag() {
        bag = Bag(id=94,name = "Frite",price = 20.0.toFloat(), logoUrl =  "logo",descr =  "desc",qty = 1,rest = 99)
        db?.getBagDao()?.addBag(bag)
        bag.price = 30.0.toFloat();
        db?.getBagDao()?.updateBag(bag)
        val list = db?.getBagDao()?.getBagById(bag.id!!)
        assertEquals(bag.price,list?.get(0)?.price)
    }

    @After
    fun closeDb(){
        db?.close();
    }
}

