package com.example.lvl_up.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. ANOTACIÓN @Database
// Se listan TODAS las entidades (tablas) que tendrá la DB.
@Database(
    entities = [Product::class, User::class], // <-- ¡Ambas tablas aquí!
    version = 1, // La versión de la base de datos
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // 2. EXPOSICIÓN DE DAOs
    // Se expone un método abstracto para CADA DAO.
    abstract fun productDao(): ProductDao // Para las operaciones de Product
    abstract fun userDao(): UserDAO     // Para las operaciones de User

    // 3. SINGLETON PATTERN (Para una única instancia)
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lvl_up_db" // El nombre del archivo de la DB
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}