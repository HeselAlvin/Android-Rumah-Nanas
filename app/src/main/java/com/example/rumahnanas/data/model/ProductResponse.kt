package com.example.rumahnanas.data.model

import com.google.gson.annotations.SerializedName


data class ProductResponse (
    val message: String?,
    @SerializedName("data")
    val data: List<Product>

)