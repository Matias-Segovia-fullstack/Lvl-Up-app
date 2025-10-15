package com.example.lvl_up.data

data class Product(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val category: String,
    val price: String,
    val stock: Int
)

val sampleProducts = listOf(
    Product(1, "imagenes/ejemplo_pc1.jpg", "PC Gamer Ultimate", "Computadores", "\$1.200.000", 10),
    Product(2, "imagenes/ejemplo_gpu1.jpg", "NVIDIA RTX 4080", "Tarjetas de Video", "\$950.000", 6),
    Product(3, "imagenes/ejemplo_mouse1.jpg", "Mouse Gamer RGB", "Periféricos", "\$35.000", 42)
)
