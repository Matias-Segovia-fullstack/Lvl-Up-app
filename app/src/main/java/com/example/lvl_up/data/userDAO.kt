package com.example.lvl_up.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    // --- 2. Operación de Escritura: Actualizar ---
    @Update
    suspend fun updateUser(user: User)

    // --- 3. Operación de Lectura: Lista Dinámica (para tu UI) ---
    @Query("SELECT * FROM users_table ORDER BY id ASC")
    fun getAllUsers(): Flow<List<User>>

    // --- 4. Operación de Lectura: Obtener por ID (para editar) ---
    @Query("SELECT * FROM users_table WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT COUNT(id) FROM users_table")
    fun getUserCount(): Flow<Int>
}