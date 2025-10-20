package com.example.lvl_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lvl_up.data.CarritoRepository
import com.example.lvl_up.data.ProductRepository


class CarritoViewModelFactory(
    private val carritoRepository: CarritoRepository,
    private val productRepository: ProductRepository // ✅ AÑADE EL REPOSITORIO DE PRODUCTOS
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarritoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // ✅ PÁSALE EL NUEVO REPOSITORIO AL VIEWMODEL
            return CarritoViewModel(carritoRepository, productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
