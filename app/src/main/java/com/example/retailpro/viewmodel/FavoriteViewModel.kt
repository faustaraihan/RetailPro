package com.example.retailpro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retailpro.data.ProductRepository
import com.example.retailpro.data.local.LocalDatabase
import com.example.retailpro.data.remote.model.Product
import com.example.retailpro.data.remote.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository = ProductRepository(ApiClient.apiClient, LocalDatabase.getDatabase(application).getProductDao())

    private val _favProduct = MutableLiveData<List<com.example.retailpro.data.local.entity.Product>>()
    val favProduct: LiveData<List<com.example.retailpro.data.local.entity.Product>> get() = _favProduct

    fun getFavProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favProducts = repository.getFavProduct()
                _favProduct.postValue(favProducts)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

