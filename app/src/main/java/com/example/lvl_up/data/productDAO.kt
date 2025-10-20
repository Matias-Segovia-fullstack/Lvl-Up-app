package com.example.lvl_up.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Query("SELECT * FROM products_table ORDER BY id ASC")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products_table WHERE id = :productId")
    suspend fun getProductById(productId: Int): Product?

    @Query("SELECT COUNT(id) FROM products_table")
    fun getProductCount(): Flow<Int>

    @Query("SELECT * FROM products_table WHERE category = :category")
    fun getProductsByCategory(category: String): Flow<List<Product>>

    @Query("UPDATE products_table SET stock = stock - :quantity WHERE id = :productId")
    suspend fun decreaseStock(productId: Int, quantity: Int)
}