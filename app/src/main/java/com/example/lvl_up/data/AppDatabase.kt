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
    version = 3,
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
                                val productDao = getDatabase(context).productDao()

                                val adminUser = User(
                                    nombre = "Admin Duoc",
                                    rut = "12.345.678-9",
                                    correo = "admin@duocuc.cl",
                                    contrasena = "admin123",
                                    rol = "Administrador",
                                    avatarUrl = "urlAdmin"
                                )
                                userDao.insertUser(adminUser)
                                val clientUser = User(
                                    nombre = "Cliente Gmail",
                                    rut = "98.765.432-1",
                                    correo = "cliente@gmail.com",
                                    contrasena = "cliente123",
                                    rol = "Cliente",
                                    avatarUrl = "urlCliente"
                                )
                                userDao.insertUser(clientUser)

                                val catan = Product(
                                    imageUrl = "catan_url",
                                    name = "Catan",
                                    category = "Juegos de mesa",
                                    price = "29.990",
                                    stock = 10)
                                productDao.insertProduct(catan)
                                val carcassonne = Product(
                                    imageUrl = "carcassonne_url",
                                    name = "Carcassonne",
                                    category = "Juegos de mesa",
                                    price = "24.990",
                                    stock = 5)
                                productDao.insertProduct(carcassonne)
                                val cont_xbox = Product(
                                    imageUrl = "cont_xbox_url",
                                    name = "Controlador inalambrico xbox series X",
                                    category = "Accesorios",
                                    price = "59.990",
                                    stock = 20)
                                productDao.insertProduct(cont_xbox)
                                val auri_gam = Product(
                                    imageUrl = "auri_gam_url",
                                    name = "Auriculares Gamer hyperX Cloud II ",
                                    category = "Accesorios",
                                    price = "79.990",
                                    stock = 20)
                                productDao.insertProduct(auri_gam)
                                val play5 = Product(
                                    imageUrl = "play5_url",
                                    name = "Playstation 5",
                                    category = "Consolas",
                                    price = "549.990",
                                    stock = 8)
                                productDao.insertProduct(play5)
                                val pc_gamer = Product(
                                    imageUrl = "pc_gamer_url",
                                    name = "PC gamer ASUS ROG STRIX",
                                    category = "Computadores gamers",
                                    price = "1299.990",
                                    stock = 5)
                                productDao.insertProduct(pc_gamer)
                                val silla_gamer_st = Product(
                                    imageUrl = "silla_gamer_st_url",
                                    name = "Silla gamer Secretlab titan",
                                    category = "Sillas gamers",
                                    price = "349.990",
                                    stock = 18)
                                productDao.insertProduct(silla_gamer_st)
                                val mousepad_razer = Product(
                                    imageUrl = "mousepad_razer_url",
                                    name = "Mousepad Razer goliathus extended chroma",
                                    category = "Mousepad",
                                    price = "29.990",
                                    stock = 30)
                                productDao.insertProduct(mousepad_razer)
                                val polera_per = Product(
                                    imageUrl = "polera_per_url",
                                    name = "Polera gamer personalizada 'level-up'croma",
                                    category = "Poleras y polerones personalizadas",
                                    price = "14.990",
                                    stock = 18)
                                productDao.insertProduct(polera_per)
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