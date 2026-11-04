package com.example.rumahnanas.data.model

import kotlinx.coroutines.internal.OpDescriptor

data class Product (
    val id: Int,
    val name: String?,
    val price: String?,
    val image: String?,


    val description: String? = "Lorem Ipsum is simply dummy text of the printing and " +
            "typesetting industry. Lorem Ipsum has been the industry's standard dummy " +
            "text ever since the 1500s, when an unknown printer took a galley of type and scrambled " +
            "it to make a type specimen book. It has survived not only five centuries, " +
            "but also the leap into electronic typesetting, remaining essentially unchanged"
)