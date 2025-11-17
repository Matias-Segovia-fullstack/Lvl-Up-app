package com.example.lvl_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lvl_up.data.CarritoRepository
import com.example.lvl_up.data.ItemCarrito
import com.example.lvl_up.data.Product
import com.example.lvl_up.data.ProductRepository
import com.example.lvl_up.data.UserManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf // <-- IMPORTACIÓN AÑADIDA
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class CarritoViewModel(
    private val repository: CarritoRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val currentUserId = UserManager.currentUserId

    val itemsDelCarrito: StateFlow<List<ItemCarrito>> =
        currentUserId?.let { userId ->
            repository.getCartItems(userId)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = emptyList()
                )
        } ?: flowOf<List<ItemCarrito>>(emptyList())
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
            currentUserId?.let { userId ->
                repository.clearCart(userId)
            }
        }
    }

    fun addToCart(product: Product, quantity: Int) {
        viewModelScope.launch {
            currentUserId?.let { userId ->
                repository.addToCart(product, userId, quantity)
            }
        }
    }

    fun checkout() {
        viewModelScope.launch {
            currentUserId?.let { userId ->
                // 1. Obtiene la lista actual de items en el carrito
                val itemsToCheckout = itemsDelCarrito.value

                // 2. Por cada item, reduce el stock en la base de datos
                for (item in itemsToCheckout) {
                    productRepository.decreaseStock(item.productId.toInt(), item.cantidad)
                }

                // 3. Vacía el carrito del usuario
                repository.clearCart(userId)
            }
        }
    }
}