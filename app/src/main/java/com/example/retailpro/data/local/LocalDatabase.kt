package com.example.retailpro.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.retailpro.data.local.dao.ProductDao
import com.example.retailpro.data.local.entity.Product
import com.example.retailpro.data.local.entity.RatingConverter

@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(RatingConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "local_database"

                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}