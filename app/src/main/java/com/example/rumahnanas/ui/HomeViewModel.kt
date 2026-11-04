package com.example.rumahnanas.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rumahnanas.data.api.ApiClient
import com.example.rumahnanas.data.model.Product
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel(){

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getProducts(){
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.getProducts()
                if (response.isSuccessful && response.body() != null){
                    _products.value = response.body()!!.data
                }else {
                    val errorAsli = response.errorBody()?.string()
                    Log.e("Cek_API", "Error dari laravel: $errorAsli")
                    _error.value = "Gagal Mengambil data Produk"
                }
            } catch (e: Exception){
                _error.value = "Koneksi Error: ${e.message}"
            }
        }
    }

 }
