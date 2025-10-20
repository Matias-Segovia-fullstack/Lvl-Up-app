package com.example.lvl_up.data

import kotlinx.coroutines.flow.Flow


class CarritoRepository(private val carritoDao: CarritoDao) {

    fun getCartItems(userId: Int): Flow<List<ItemCarrito>> = carritoDao.getCartItems(userId)

    suspend fun addToCart(product: Product, userId: Int) {
        val existingItem = carritoDao.findItemInCart(userId, product.id)
        if (existingItem != null) {

            val updatedItem = existingItem.copy(cantidad = existingItem.cantidad + 1)
            carritoDao.update(updatedItem)
        } else {
            val newItem = ItemCarrito(
                productId = product.id,
                userId = userId,
                cantidad = 1,
                name = product.name,
                price = product.price.toDoubleOrNull() ?: 0.0,
                imageUrl = product.imageUrl
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
