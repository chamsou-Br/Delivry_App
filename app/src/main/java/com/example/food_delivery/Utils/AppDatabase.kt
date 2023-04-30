package com.example.food_delivery.Utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.food_delivery.modals.Dao.bagDao
import com.example.food_delivery.modals.Dao.userDao
import com.example.food_delivery.modals.Entity.Bag
import com.example.food_delivery.modals.Entity.User

@Database(entities = [User::class , Bag::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): userDao
    abstract fun getBagDao() : bagDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun buildDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context, AppDatabase::class.java,
                        "db_rest4").allowMainThreadQueries().build() }
            return INSTANCE
        }
    }

}