package com.example.lvl_up.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val nombre: String,
    val rut: String,
    val correo: String,
    val rol: String

)


val sampleUsers = listOf(
    User(1,"Juan Pérez", "12.345.678-9", "juan.perez@mail.com", "Admin"),
    User(2,"María González", "23.456.789-1", "maria.gonzalez@mail.com", "Cliente"),
    User(3,"Carlos Ramírez", "34.567.890-2", "carlos.ramirez@mail.com", "Moderador"),
    User(4,"Ana López", "45.678.901-3", "ana.lopez@mail.com", "Usuario"),
    User(5,"Luis Martínez", "56.789.012-4", "luis.martinez@mail.com", "Admin"),
    User(6,"Sofía Fernández", "67.890.123-5", "sofia.fernandez@mail.com", "Usuario"),
    User(7,"Diego Torres", "78.901.234-6", "diego.torres@mail.com", "Usuario"),
    User(8,"Camila Rojas", "89.012.345-7", "camila.rojas@mail.com", "Moderador"),
    User(9,"Pedro Vargas", "90.123.456-8", "pedro.vargas@mail.com", "Usuario"),
    User(10,"Valentina Soto", "01.234.567-9", "valentina.soto@mail.com", "Admin")
)
