
package com.example.lvl_up

import android.app.Application
import com.example.lvl_up.data.ProductRepository
import com.example.lvl_up.data.UserRepository
import com.example.lvl_up.data.CarritoRepository



class LvlUpApplication : Application() {

    // Inicialización de la DB (Singleton)
    val database by lazy { AppDatabase.getDatabase(this) }

    // Inicialización del Repositorio de Producto
    val productRepository by lazy {
        ProductRepository(database.productDao())
    }

    val userRepository by lazy {
        UserRepository(database.userDao())
    }

    val carritoRepository by lazy {
        CarritoRepository(database.carritoDao())
    }
}