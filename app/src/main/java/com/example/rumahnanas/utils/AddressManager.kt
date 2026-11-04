package com.example.rumahnanas.utils

import android.content.Context
import com.example.rumahnanas.data.model.Address
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AddressManager {
    private const val PREFS_NAME = "RumahNanasAddress"
    private const val ADDRESS_KEY = "AddressList"
    private const val SELECTED_ADDRESS_KEY = "SelectedAddress"

    fun saveAddress(context: Context, address: Address) {
        try {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val gson = Gson()
            val json = prefs.getString(ADDRESS_KEY, null)

            val type = object : TypeToken<MutableList<Address>>() {}.type
            val addressList: MutableList<Address> = if (json != null) {
                gson.fromJson(json, type) ?: mutableListOf()
            } else {
                mutableListOf()
            }

            addressList.add(address)
            prefs.edit().putString(ADDRESS_KEY, gson.toJson(addressList)).apply()
        } catch (e: Exception) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val newList = mutableListOf(address)
            prefs.edit().putString(ADDRESS_KEY, Gson().toJson(newList)).apply()
        }
    }

    fun getAddressList(context: Context): List<Address> {
        return try {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val json = prefs.getString(ADDRESS_KEY, null) ?: return emptyList()
            val type = object : TypeToken<List<Address>>() {}.type
            Gson().fromJson(json, type) ?: emptyList()
        } catch (e: Exception){
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().remove(ADDRESS_KEY).apply()
            emptyList()
        }
    }

    fun saveSelectedAddress(context: Context, address: Address) {
        try {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString(SELECTED_ADDRESS_KEY, Gson().toJson(address)).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSelectedAddress(context: Context): Address? {
        return try {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val json = prefs.getString(SELECTED_ADDRESS_KEY, null) ?: return null
            Gson().fromJson(json, Address::class.java)
        } catch (e: Exception) {
            null
        }
    }
}