package com.example.lvl_up

import android.app.Application
import com.example.lvl_up.data.ProductRepository
import com.example.lvl_up.data.UserRepository
import com.example.lvl_up.data.CarritoRepository
// No se necesita el import de AppDatabase

class LvlUpApplication : Application() {

    // Se elimina toda la inicialización de la base de datos local (Room)

    // Los repositorios se inicializan sin argumentos (usan directamente Retrofit)
    val productRepository by lazy {
        ProductRepository()
    }

    val userRepository by lazy {
        UserRepository()
    }

    val carritoRepository by lazy {
        CarritoRepository()
    }
}