package com.example.lvl_up.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow // ⬅️ AÑADIR ESTE IMPORT
import kotlinx.coroutines.flow.catch
import java.util.Collections.emptyList

class ProductRepository {
    private val apiService = RetrofitClient.apiService

    // CORRECCIÓN: Usar 'flow' y 'emit' para llamar a la función suspend
    val allProducts: Flow<List<Product>> = flow {
        emit(apiService.getAllProducts())
    }.catch {
        emit(emptyList())
    }

    // CORRECCIÓN: Usar 'flow' para el conteo
    val productCount: Flow<Int> = flow {
        val count = apiService.countProducts()
        emit(count.toInt())
    }.catch {
        emit(0)
    }

    suspend fun insert(product: Product) {
        apiService.saveOrUpdateProduct(product)
    }

    suspend fun update(product: Product) {
        apiService.saveOrUpdateProduct(product)
    }

    suspend fun getProductById(id: Int): Product? {
        return try {
            apiService.getProductById(id.toLong())
        } catch (e: Exception) {
            null
        }
    }

    fun getProductsByCategory(category: String): Flow<List<Product>> {
        return allProducts
    }

    suspend fun decreaseStock(productId: Int, quantity: Int) {
        // Lógica de BE faltante: llamar a un endpoint de checkout o disminución de stock
    }
}