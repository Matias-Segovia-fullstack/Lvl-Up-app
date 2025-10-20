package com.example.lvl_up.data

import kotlinx.coroutines.flow.Flow


class CarritoRepository(private val carritoDao: CarritoDao) {

    fun getCartItems(userId: Int): Flow<List<ItemCarrito>> = carritoDao.getCartItems(userId)

    suspend fun addToCart(product: Product, userId: Int, quantity: Int = 1) {
        if (quantity <= 0) return

        val existingItem = carritoDao.findItemInCart(userId, product.id)
        val priceAsDouble = product.price.replace(".", "").toDoubleOrNull() ?: 0.0

        if (existingItem != null) {
            val updatedItem = existingItem.copy(cantidad = existingItem.cantidad + quantity)
            carritoDao.update(updatedItem)
        } else {
            val newItem = ItemCarrito(
                productId = product.id,
                userId = userId,
                cantidad = quantity,
                name = product.name,
                price = priceAsDouble,
                imageUrl = product.imageUrl,
                stock = product.stock
            )
            carritoDao.insert(newItem)
        }
    }

    suspend fun updateQuantity(item: ItemCarrito) {
        if (item.cantidad > 0) {
            carritoDao.update(item)
        } else {
            carritoDao.delete(item)
        }
    }


    suspend fun clearCart(userId: Int) {
        carritoDao.clearCart(userId)
    }
}
