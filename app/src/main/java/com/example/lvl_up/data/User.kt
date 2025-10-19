package com.example.lvl_up.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,
    val rut: String,
    val correo: String,
    val contrasena: String,
    val rol: String,
    val avatarUrl: String

)



