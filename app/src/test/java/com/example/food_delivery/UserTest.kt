package com.example.food_delivery

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.food_delivery.Utils.AppDatabase
import com.example.food_delivery.modals.Entity.User
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class UserTest {
    private lateinit var db: AppDatabase
    private lateinit var user : User;
    @Before
    fun initDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }
    @Test
    fun testInsertAndGetUser() {
        user = User(99,"chamsou","berkane","jc_berkane@esi.dz","chamsou2002")
        db?.getUserDao()?.addUser(user)
        val list = db?.getUserDao()?.getByEmail("jc_berkane@esi.dz")
        println(list);
        assertEquals(user,list?.get(0))
    }
    @After
    fun closeDb(){
        db?.getUserDao()?.delete(user);
        db?.close();
    }
}

