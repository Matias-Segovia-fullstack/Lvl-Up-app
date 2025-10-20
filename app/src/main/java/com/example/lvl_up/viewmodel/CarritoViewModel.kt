package com.example.lvl_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lvl_up.data.ItemCarrito
import com.example.lvl_up.data.CarritoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarritoViewModel(private val repository: CarritoRepository) : ViewModel() {

    private val currentUserId = 2


    val itemsDelCarrito: StateFlow<List<ItemCarrito>> = repository.getCartItems(currentUserId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun actualizarCantidad(item: ItemCarrito, cambio: Int) {
        viewModelScope.launch {
            val itemActualizado = item.copy(cantidad = item.cantidad + cambio)
            repository.updateQuantity(itemActualizado)
        }
    }


    fun eliminarItem(item: ItemCarrito) {
        viewModelScope.launch {
            val itemAEliminar = item.copy(cantidad = 0)
            repository.updateQuantity(itemAEliminar)
        }
    }


    fun vaciarCarrito() {
        viewModelScope.launch {
            repository.clearCart(currentUserId)
        }
    }
}



