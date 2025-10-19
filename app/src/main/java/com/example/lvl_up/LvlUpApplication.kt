
package com.example.lvl_up

import android.app.Application
import com.example.lvl_up.data.AppDatabase
import com.example.lvl_up.data.ProductRepository

class LvlUpApplication : Application() {

    // Inicialización de la DB (Singleton)
    val database by lazy { AppDatabase.getDatabase(this) }

    // Inicialización del Repositorio de Producto
    val productRepository by lazy {
        ProductRepository(database.productDao())
    }
}