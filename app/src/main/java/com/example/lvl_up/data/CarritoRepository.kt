package com.example.lvl_up.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import java.util.Collections.emptyList

class CarritoRepository {
    private val apiService = RetrofitClient.apiService

    fun getCartItems(userId: Int): Flow<List<ItemCarrito>> = flow {
        emit(apiService.getCartDetailsByUserId(userId.toLong()))
    }.catch {
        emit(emptyList())
    }

    suspend fun addToCart(product: Product, userId: Int, quantity: Int = 1) {
        if (product.id == null || product.id == 0L) return

        val payload = mapOf(
            "userId" to userId.toLong(),
            "productId" to product.id,
            "cantidad" to quantity
        )
        apiService.addItemToCart(payload)
    }

    suspend fun updateQuantity(item: ItemCarrito) {
        if (item.id != null && item.id > 0 && item.cantidad <= 0) {
            apiService.removeItemFromCart(item.id)
        }
    }

    suspend fun clearCart(userId: Int) {
        apiService.clearCart(userId.toLong())
    }
}