package com.example.food_delivery.modals.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.food_delivery.modals.Entity.User

@Dao
interface userDao {

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>


    @Query("SELECT * FROM user WHERE email = :email  ")
    fun getByEmail(email: String): List<User>

    @Insert
    fun addUser(vararg user: User)

    @Delete
    fun delete(user: User)
}