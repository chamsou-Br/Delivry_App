package com.example.food_delivery.modals.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.food_delivery.modals.Entity.Bag

@Dao
interface bagDao {

    @Query("SELECT * FROM Bag where rest= :rest")
    fun getAllBags(rest : String): List<Bag>

    @Query("SELECT * FROM Bag")
    fun getAllBags(): List<Bag>

    @Query("SELECT * FROM Bag WHERE id = :id ")
    fun getBagById(id: Int): List<Bag>


    @Query("SELECT * FROM Bag WHERE name = :name ")
    fun getBagByName(name: String?): List<Bag>

    @Insert
    fun addBag(vararg bag: Bag)

    @Update
    fun updateBag(bag: Bag)

    @Delete
    fun delete(bag: Bag)

    @Query("DELETE FROM Bag where rest = :rest")
    fun deleteByRest(rest : String)
}