package com.example.lvl_up.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    // para crear usuario
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    // para actualizar actualizar
    @Update
    suspend fun updateUser(user: User)

    // para ver todos los usuarios
    @Query("SELECT * FROM users_table ORDER BY id ASC")
    fun getAllUsers(): Flow<List<User>>

    // para obtener por el id
    @Query("SELECT * FROM users_table WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    //para obtener el numero de usuarios
    @Query("SELECT COUNT(id) FROM users_table")
    fun getUserCount(): Flow<Int>

    //para validar en el login
    @Query("SELECT * FROM users_table WHERE correo = :email AND contrasena = :password LIMIT 1")
    suspend fun findUserByCredentials(email: String, password: String): User?

    @Query("SELECT * FROM users_table WHERE rut = :rut LIMIT 1")
    suspend fun findByRut(rut: String): User?

    @Query("SELECT * FROM users_table WHERE correo = :email LIMIT 1")
    suspend fun findByEmail(email: String): User?
}
