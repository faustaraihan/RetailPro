package com.example.retailpro.data

import com.example.retailpro.data.local.dao.ProductDao
import com.example.retailpro.data.remote.network.ApiService
import com.example.retailpro.data.remote.model.Product

class ProductRepository(private val apiService: ApiService, private val productDao: ProductDao) {
    suspend fun getProduct(): List<Product> {
        return apiService.getProducts()
    }

    suspend fun getProductDetail(id: Int): Product {
        return apiService.getProducts(id)
    }

    suspend fun getProductByKeyword(keyword: String): List<Product> {
        return try {
            val products = apiService.getProducts()
            products.filter { it.title.contains(keyword, ignoreCase = true) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getFavProduct(): List<com.example.retailpro.data.local.entity.Product> {
        return productDao.getAllProducts()
    }

    suspend fun isProductExist(id: Int): Int {
        return productDao.isProductExist(id)
    }

    suspend fun insertProduct(product: com.example.retailpro.data.local.entity.Product) {
        productDao.insertProduct(product)
    }

    suspend fun deleteProduct(id: Int) {
        productDao.deleteProduct(id)
    }
}