package com.example.lvl_up.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    // --- 1. Operación de Escritura: Insertar o Reemplazar ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    // --- 2. Operación de Escritura: Actualizar ---
    @Update
    suspend fun updateProduct(product: Product)

    // --- 3. Operación de Lectura: Lista Dinámica (para tu UI) ---
    @Query("SELECT * FROM products_table ORDER BY id ASC")
    fun getAllProducts(): Flow<List<Product>>

    // --- 4. Operación de Lectura: Obtener por ID (para editar) ---
    @Query("SELECT * FROM products_table WHERE id = :productId")
    suspend fun getProductById(productId: Int): Product?

    @Query("SELECT COUNT(id) FROM products_table")
    fun getProductCount(): Flow<Int>

    @Query("SELECT * FROM products_table WHERE category = :category")
    fun getProductsByCategory(category: String): Flow<List<Product>>

    @Query("UPDATE products_table SET stock = stock - :quantity WHERE id = :productId")
    suspend fun decreaseStock(productId: Int, quantity: Int)
}