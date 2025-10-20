package com.example.lvl_up.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Product::class, User::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDAO


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lvl_up_db" // El nombre del archivo de la DB
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                val userDao = getDatabase(context).userDao()
                                // Creamos y guardamos el usuario Administrador
                                val adminUser = User(
                                    nombre = "Admin Duoc",
                                    rut = "12.345.678-9",
                                    correo = "admin@duocuc.cl",
                                    contrasena = "admin123",
                                    rol = "Administrador",
                                    avatarUrl = "urlAdmin"
                                )
                                userDao.insertUser(adminUser)
                                // Creamos y guardamos el usuario Cliente
                                val clientUser = User(
                                    nombre = "Cliente Gmail",
                                    rut = "98.765.432-1",
                                    correo = "cliente@gmail.com",
                                    contrasena = "cliente123",
                                    rol = "Cliente",
                                    avatarUrl = "urlCliente"
                                )
                                userDao.insertUser(clientUser)
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}