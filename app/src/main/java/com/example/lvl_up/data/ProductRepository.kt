package com.example.lvl_up.data

import kotlinx.coroutines.flow.Flow

// 1. El Repositorio recibe el DAO en su constructor.
class ProductRepository(private val productDao: ProductDao) {

    // 2. LECTURA DINÁMICA (Para la lista en Compose)
    // Expone el Flow<List<Product>> que viene del DAO.
    // Esta es la variable que tu ViewModel observará.
    val allProducts: Flow<List<Product>> = productDao.getAllProducts()

    val productCount: Flow<Int> = productDao.getProductCount()
    // 3. OPERACIONES DE ESCRITURA (Insertar)
    // Llama a la función suspend del DAO para guardar.
    suspend fun insert(product: Product) {
        productDao.insertProduct(product)
    }

    // 4. OPERACIONES DE ESCRITURA (Actualizar, para la edición)
    // Llama a la función suspend del DAO para actualizar.
    suspend fun update(product: Product) {
        productDao.updateProduct(product)
    }

    // 5. OBTENER UN OBJETO ESPECÍFICO (Para la pantalla de edición)
    // Llama al DAO para buscar un solo producto por su ID.
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