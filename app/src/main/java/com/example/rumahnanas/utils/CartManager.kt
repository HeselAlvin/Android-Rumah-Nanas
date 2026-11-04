package com.example.rumahnanas.utils

import android.content.Context
import androidx.annotation.UiContext
import com.example.rumahnanas.data.model.Cart
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CartManager {
    val cartItems = mutableListOf<Cart>()

    var checkoutItems = mutableListOf<Cart>()
    var totalCheckoutPrice: Int = 0

    fun loadCart(context: Context) {
        val prefs = context.getSharedPreferences("RUMAH_NANAS_PREFS", Context.MODE_PRIVATE)
        val json = prefs.getString("CART_DATA", null)
        if (json != null ){
            val type = object : TypeToken<MutableList<Cart>>(){}.type
            val savedData: MutableList<Cart> = Gson().fromJson(json, type)
            cartItems.clear()
            cartItems.addAll(savedData)
        }
    }

    fun saveCart(context: Context){
        val prefs = context.getSharedPreferences("RUMAH_NANAS_PREFS", Context.MODE_PRIVATE)
        val json = Gson().toJson(cartItems)
        prefs.edit().putString("CART_DATA", json).apply()
    }

    fun addToCart(context: Context, item: Cart){
        loadCart(context)

        val existingItem = cartItems.find { it.name == item.name }

        if(existingItem != null){
            existingItem.quantity += item.quantity
        }else{
            cartItems.add(item)
        }

        saveCart(context)
    }

    fun clearCheckoutItems(context: Context) {
        cartItems.removeAll(checkoutItems)
        checkoutItems.clear()
        totalCheckoutPrice = 0

        saveCart(context)
    }
}