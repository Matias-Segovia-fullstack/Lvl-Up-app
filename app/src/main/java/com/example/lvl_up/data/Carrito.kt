package com.example.lvl_up.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "items_carrito",
    indices = [Index(value = ["productId", "userId"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ItemCarrito(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val productId: Int,
    val userId: Int,

    var cantidad: Int,

    val name: String,
    val price: Double,
    val imageUrl: String
)

