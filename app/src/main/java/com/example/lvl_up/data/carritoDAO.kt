package com.example.lvl_up.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface CarritoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ItemCarrito)

    @Update
    suspend fun update(item: ItemCarrito)

    @Delete
    suspend fun delete(item: ItemCarrito)

    @Query("SELECT * FROM items_carrito WHERE userId = :userId ORDER BY id DESC")
    fun getCartItems(userId: Int): Flow<List<ItemCarrito>>

    @Query("DELETE FROM items_carrito WHERE userId = :userId")
    suspend fun clearCart(userId: Int)

    @Query("SELECT * FROM items_carrito WHERE userId = :userId AND productId = :productId LIMIT 1")
    suspend fun findItemInCart(userId: Int, productId: Int): ItemCarrito?
}
