package com.example.lvl_up.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow // ⬅️ AÑADIR ESTE IMPORT
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.Collections.emptyList

class UserRepository {
    private val apiService = RetrofitClient.apiService

    // CORRECCIÓN: Usar 'flow' y 'emit' para llamar a la función suspend
    val allUsers: Flow<List<User>> = flow {
        emit(apiService.getAllUsers())
    }.catch {
        emit(emptyList())
    }

    // CORRECCIÓN: Usar 'flow' para el conteo
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
            null
        }
    }

    suspend fun findUserByCredentials(email: String, password: String): User? {
        return try {
            apiService.loginUser(UserCredentials(email, password))
        } catch (e: Exception) {
            null
        }
    }

    suspend fun findByRut(rut: String): User? { return null }
    suspend fun findByEmail(email: String): User? { return null }
}