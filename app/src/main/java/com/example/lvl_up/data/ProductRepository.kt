package com.example.lvl_up.data

import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: Flow<List<Product>> = productDao.getAllProducts()

    val productCount: Flow<Int> = productDao.getProductCount()

    suspend fun insert(product: Product) {
        productDao.insertProduct(product)
    }

    suspend fun update(product: Product) {
        productDao.updateProduct(product)
    }

    suspend fun getProductById(id: Int): Product? {
        return productDao.getProductById(id)
    }

    fun getProductsByCategory(category: String): Flow<List<Product>> {
        return productDao.getProductsByCategory(category)
    }

    suspend fun decreaseStock(productId: Int, quantity: Int) {
        productDao.decreaseStock(productId, quantity)
    }
}