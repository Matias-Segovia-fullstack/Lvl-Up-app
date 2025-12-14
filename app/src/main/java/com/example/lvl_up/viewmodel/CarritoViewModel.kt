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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.flatMapLatest // <--- ¡IMPORTACIÓN CLAVE!
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class CarritoViewModel(
    private val repository: CarritoRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    // 🔴 ELIMINAMOS la línea de inicialización estática 'private val currentUserId = UserManager.currentUserId'

    val itemsDelCarrito: StateFlow<List<ItemCarrito>> =
        UserManager.currentUserIdFlow // <-- Usa el Flow reactivo
            .flatMapLatest { userId -> // Se re-ejecuta cada vez que el userId cambia
                if (userId != null) {
                    repository.getCartItems(userId) // Carga el carrito del usuario logueado
                } else {
                    flowOf(emptyList()) // Si no hay usuario, muestra un carrito vacío
                }
            }
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
            UserManager.currentUserId?.let { userId ->
                repository.clearCart(userId)
            }
        }
    }

    fun addToCart(product: Product, quantity: Int) {
        viewModelScope.launch {
            try {

                val userId = UserManager.currentUserId
                if (userId != null && product.id != null && product.id > 0) {
                    repository.addToCart(product, userId, quantity)
                    println("Producto agregado con éxito: ${product.name}")
                } else {
                    println("Error: Usuario no logueado o ID de producto inválido")
                }
            } catch (e: Exception) {

                println("Error al agregar al carrito: ${e.message}")
                e.printStackTrace()
            }
        }
    }
    fun checkout() {
        viewModelScope.launch {
            UserManager.currentUserId?.let { userId ->
                // 1. Obtiene la lista actual de items en el carrito
                val itemsToCheckout = itemsDelCarrito.value

                // 2. Por cada item, reduce el stock en la base de datos
                for (item in itemsToCheckout) {
                    productRepository.decreaseStock(item.productId, item.cantidad)
                }

                // 3. Vacía el carrito del usuario
                repository.clearCart(userId)
            }
        }
    }
}