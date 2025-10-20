package com.example.lvl_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lvl_up.data.User
import com.example.lvl_up.data.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val userListState: StateFlow<List<User>> =
        repository.allUsers // <-- Aquí toma el Flow del DAO a través del Repositorio
            .stateIn(
                scope = viewModelScope, // Ejecuta esto mientras el ViewModel esté vivo
                started = SharingStarted.WhileSubscribed(5000), // Mantiene activo el Flow si es observado
                initialValue = emptyList() // Valor por defecto antes de cargar datos
            )

    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.update(user)
        }
    }

    suspend fun getUserForEdit(id: Int): User? {
        return repository.getUserById(id)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return repository.findUserByCredentials(email, password)
    }

    suspend fun isRutTaken(rut: String): Boolean {
        return repository.findByRut(rut) != null
    }

    suspend fun isEmailTaken(email: String): Boolean {
        return repository.findByEmail(email) != null
    }
}