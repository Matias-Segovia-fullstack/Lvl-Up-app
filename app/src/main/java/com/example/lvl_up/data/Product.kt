package com.example.lvl_up.data

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("idProducto")
    val id: Long? = null,

    val imageUrl: String,

    @SerializedName("nombreProducto")
    val name: String,

    val category: String,
    val price: Int,
    val stock: Int
)

data class StockDecreaseRequest(
    val quantity: Int
)