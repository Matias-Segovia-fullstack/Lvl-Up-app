package com.example.lvl_up.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "products_table",
    indices = [
        Index(value = ["name"], unique = true)
    ])
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val imageUrl: String,
    val name: String,
    val category: String,
    val price: String,
    val stock: Int
)

