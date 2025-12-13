package com.example.lvl_up.data

import com.google.gson.annotations.SerializedName

// Mapea la respuesta JSON que devuelve tu JwtAuthenticationFilter en el backend
data class LoginResponse(
    // El campo que contiene el token JWT
    @SerializedName("token")
    val token: String,

    @SerializedName("correo")
    val correo: String?,

    @SerializedName("message")
    val message: String?
)