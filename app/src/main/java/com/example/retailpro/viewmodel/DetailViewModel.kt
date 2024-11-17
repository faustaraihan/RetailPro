package com.example.retailpro.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.retailpro.R
import com.example.retailpro.data.ProductRepository
import com.example.retailpro.data.local.LocalDatabase
import com.example.retailpro.data.remote.model.Product
import com.example.retailpro.data.remote.network.ApiClient
import com.example.retailpro.databinding.ActivityDetailBinding
import com.example.retailpro.util.ProductUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel (private val application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository = ProductRepository(ApiClient.apiClient, LocalDatabase.getDatabase(application).getProductDao())

    private val _productDetail = MutableLiveData<Product>()
    val productDetail: MutableLiveData<Product> get() = _productDetail

    private val _exception = MutableLiveData<Boolean>()
    val exception: MutableLiveData<Boolean> get() = _exception

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved: MutableLiveData<Boolean> get() = _isSaved

    fun getProductDetail(id: Int) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val detail = repository.getProductDetail(id)
                _productDetail.postValue(detail)
                _exception.postValue(false)
            } catch (e: Exception) {
                _exception.postValue(true)
            }
        }
    }

    fun isProductSaved(id: Int) {
        viewModelScope.launch (Dispatchers.IO) {
            val isSaved = repository.isProductExist(id)
            _isSaved.postValue(isSaved > 0)
        }
    }

    fun insertProduct(product: com.example.retailpro.data.local.entity.Product) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.insertProduct(product)
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(id)
        }
    }

}

