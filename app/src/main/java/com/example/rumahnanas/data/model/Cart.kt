package com.example.rumahnanas.data.model

data class Cart (
    val id: String,
    val name: String,
    val Price: Int,
    var quantity: Int,
    val imageUrl: String,
    var isSelected: Boolean = false
)