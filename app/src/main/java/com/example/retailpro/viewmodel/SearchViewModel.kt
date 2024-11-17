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

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository = ProductRepository(ApiClient.apiClient, LocalDatabase.getDatabase(application).getProductDao())

    private val _productByKeyword = MutableLiveData<List<Product>>()
    val productByKeyword: LiveData<List<Product>> get() = _productByKeyword

    private val _exception = MutableLiveData<Boolean>()
    val exception: LiveData<Boolean> = _exception

    fun getEventsByKeyword(keyword: String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val product = repository.getProductByKeyword(keyword)
                _productByKeyword.postValue(product)
            } catch (e: Exception) {
                _exception.postValue(true)
            }
        }
    }
}