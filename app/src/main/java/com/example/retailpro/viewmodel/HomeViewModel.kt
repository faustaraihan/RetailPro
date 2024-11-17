package com.example.retailpro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.retailpro.data.remote.model.Product
import com.example.retailpro.data.ProductRepository
import com.example.retailpro.data.local.LocalDatabase
import com.example.retailpro.data.remote.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository = ProductRepository(ApiClient.apiClient, LocalDatabase.getDatabase(application).getProductDao())

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _exception = MutableLiveData<Boolean>()
    val exception: LiveData<Boolean> get() = _exception

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val itemProduct = repository.getProduct()
                _products.postValue(itemProduct)
                _exception.postValue(false)
            } catch (e: Exception) {
                _exception.postValue(true)
            }
        }
    }

    fun clearException() {
        _exception.value = false
    }
}