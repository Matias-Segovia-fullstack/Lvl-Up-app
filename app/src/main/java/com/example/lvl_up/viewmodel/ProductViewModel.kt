package com.example.lvl_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lvl_up.data.Product
import com.example.lvl_up.data.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


// El ViewModel NECESITA el Repositorio para trabajar.
class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    // --- 1. ESTADO DE LECTURA (La lista dinámica para Compose) ---
    // StateFlow es un flujo que mantiene el último valor y es ideal para el estado de la UI.
    val productListState: StateFlow<List<Product>> =
        repository.allProducts // <-- Aquí toma el Flow del DAO a través del Repositorio
            .stateIn(
                scope = viewModelScope, // Ejecuta esto mientras el ViewModel esté vivo
                started = SharingStarted.WhileSubscribed(5000), // Mantiene activo el Flow si es observado
                initialValue = emptyList() // Valor por defecto antes de cargar datos
            )

    // --- 2. OPERACIONES DE ESCRITURA (Insertar, se ejecuta en un hilo seguro) ---
    fun insertProduct(product: Product) {
        // Ejecutamos el trabajo en una Coroutine (hilo ligero de Kotlin)
        // para que no bloquee la interfaz principal.
        viewModelScope.launch {
            repository.insert(product) // Llama al Repositorio
        }
    }

    // --- 3. OPERACIONES DE ESCRITURA (Actualizar) ---
    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.update(product)
        }
    }

    // --- 4. OBTENER PARA EDICIÓN (Se usa cuando se navega con el ID) ---
    suspend fun getProductForEdit(id: Int): Product? {
        // La función es 'suspend' porque la llamada al Repositorio (y al DAO) es asíncrona.
        return repository.getProductById(id)
    }

    fun getProductsByCategory(category: String): Flow<List<Product>> {
        return repository.getProductsByCategory(category)
    }
}