package com.example.retailpro.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.retailpro.data.remote.model.Rating

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)
