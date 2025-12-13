package com.example.lvl_up.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.Collections.emptyList

class UserRepository {
    private val apiService = RetrofitClient.apiService

    val allUsers: Flow<List<User>> = flow {
        emit(apiService.getAllUsers())
    }.catch {
        emit(emptyList())
    }

    val userCount: Flow<Int> = flow {
        val count = apiService.countUsers()
        emit(count.toInt())
    }.catch {
        emit(0)
    }

    suspend fun insert(user: User) {
        apiService.saveOrUpdateUser(user)
    }

    suspend fun update(user: User) {
        apiService.saveOrUpdateUser(user)
    }

    suspend fun getUserById(id: Int): User? {
        return try {
            apiService.getUserById(id.toLong())
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al obtener usuario por ID: ${e.message}")
            null
        }
    }

    suspend fun findUserByCredentials(email: String, password: String): LoginResponse   ? {
        return try {
            apiService.loginUser(UserCredentials(email, password))
        } catch (e: Exception) {
            Log.e("UserRepository", "Error en login (findUserByCredentials): ${e.message}")
            null
        }
    }

    suspend fun findByRut(rut: String): User? { return null }
    suspend fun findByEmail(email: String): User? {
        return try {
            val users = apiService.getAllUsers()
            users.find { it.correo == email }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al buscar usuario por email (protegida): ${e.message}")
            null
        }
    }
}