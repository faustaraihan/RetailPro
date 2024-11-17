package com.example.retailpro.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retailpro.data.local.entity.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT COUNT(*) FROM product WHERE id = :id")
    suspend fun isProductExist(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product)

    @Query("DELETE FROM product WHERE id = :id")
    suspend fun deleteProduct(id: Int)
}