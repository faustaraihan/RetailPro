package com.example.retailpro.data.remote.network

import com.example.retailpro.data.remote.model.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProducts(@Path("id") id: Int): Product

    @GET("products")
    suspend fun getProductByKeyword(
        @Query("keyword") keyword: String): List<Product>
}