package com.example.lvl_up.data

import com.google.gson.annotations.SerializedName
data class ItemCarrito(
    @SerializedName("idItemCarrito")
    val id: Long? = null,

    val cantidad: Int,

    @SerializedName("idUsuario")
    val userId: Long,

    @SerializedName("idProducto")
    val productId: Long,

    val name: String,
    val price: String,
    val imageUrl: String,
    val stock: Int,

    val subtotal: String
)