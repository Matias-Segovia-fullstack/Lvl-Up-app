// ProductViewModelFactory.kt

package com.example.lvl_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lvl_up.data.ProductRepository

// Le enseña a Compose cómo crear el ViewModel pasándole el Repositorio
class ProductViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Verifica que la clase solicitada sea el ProductViewModel
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            // Lo crea pasándole el Repositorio
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}