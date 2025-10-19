package com.example.lvl_up.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products_table")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val imageUrl: String,
    val name: String,
    val category: String,
    val price: String,
    val stock: Int
)

val sampleProducts = listOf(
    Product(1, "URL_AUDIFONOS_HYPERX", "Auriculares HyperX Cloud II", "Accesorios", "69.990", 25),
    // 2. PC Gamer ASUS ROG Strix
    Product(2, "URL_PC_ASUS_ROG", "PC Gamer ASUS ROG Strix", "Computadoras gamers", "1.299.990", 5),
    // 3. Control Xbox Series X Doom Eternal
    Product(3, "URL_CONTROL_XBOX_DE", "Control Xbox Series X Doom Eternal", "Accesorios", "54.990", 30),
    // 4. Mouse Gamer Logitech G502 HERO
    Product(4, "URL_MOUSE_G502", "Mouse Gamer Logitech G502 HERO", "Periféricos", "39.990", 40),
    // 5. Catan (Juego de Mesa)
    Product(5, "URL_CATAN", "Catan", "Juegos de mesa", "29.990", 15),
    // 6. Carcassonne (Juego de Mesa)
    Product(6, "URL_CARCASSONNE", "Carcassonne", "Juegos de mesa", "24.990", 18),
    // 7. Silla Gamer Secretlab Titan
    Product(7, "URL_SILLA_TITAN", "Silla Gamer Secretlab Titan", "Sillas gamer", "349.990", 12),
    // 8. Mousepad Razer Goliathus Extended Chroma
    Product(8, "URL_MOUSEPAD_RAZER", "Mousepad Razer Goliathus Extended Chroma", "Mousepads", "29.990", 22),
    // 9. PlayStation 5
    Product(9, "URL_PS5_DISC", "PlayStation 5", "Consolas", "549.000", 8),
    // 10. Polera Gamer Personalizada 'Level-Up'
    Product(10, "URL_POLERA_LVLUP", "Polera Gamer Personalizada 'Level-Up'", "Ropa personalizada", "14.990", 50),

    // --- 5 PRODUCTOS DE RELLENO BASADOS EN CATEGORÍAS ---

    // 11. Monitor (Monitores)
    Product(11, "URL_MONITOR_CURVO", "Monitor Curvo Gamer 27\" 144Hz", "Monitores", "249.990", 15),
    // 12. Teclado (Periféricos)
    Product(12, "URL_TECLADO_RGB", "Teclado Mecánico RGB HyperX Alloy", "Periféricos", "89.990", 35),
    // 13. GPU (Componentes)
    Product(13, "URL_GPU_4070", "Tarjeta de Video NVIDIA RTX 4070", "Componentes", "650.000", 7),
    // 14. Almacenamiento (Componentes)
    Product(14, "URL_SSD_1TB", "Disco SSD NVMe 1TB Samsung 990 Pro", "Almacenamiento", "119.990", 28),
    // 15. Consola (Xbox Series X)
    Product(15, "URL_XBOX_SERIES_X", "Consola Xbox Series X", "Consolas", "499.990", 10)
)
