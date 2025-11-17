package com.example.lvl_up.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import java.util.Collections.emptyList

class ProductRepository {
    private val apiService = RetrofitClient.apiService
    val allProducts: Flow<List<Product>> = flow {
        emit(apiService.getAllProducts())
    }.catch {
        emit(emptyList())
    }

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
        try {
            val request = StockDecreaseRequest(quantity)
            apiService.decreaseStock(productId.toLong(), request)
        } catch (e: Exception) {
            println("Error al descontar stock vía API para Producto ID $productId: ${e.message}")
        }
    }
}