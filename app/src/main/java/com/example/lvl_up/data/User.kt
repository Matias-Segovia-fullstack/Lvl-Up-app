package com.example.lvl_up.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("idUsuario")
    val id: Long? = null,

    @SerializedName("nombreUsuario")
    val nombre: String,

    val rut: String,
    val correo: String,
    val contrasena: String,

    @SerializedName("rolUsuario")
    val rol: String,

    val avatarUrl: String
)

data class UserCredentials(
    val correo: String,
    val contrasena: String
)