package com.example.lvl_up.data

import retrofit2.http.*

interface ApiService {
    // MÉTODOS DE USUARIO
    @POST("api/users/login")
    suspend fun loginUser(@Body credentials: UserCredentials): LoginResponse

    @POST("api/users")
    suspend fun saveOrUpdateUser(@Body user: User): User

    @GET("api/users")
    suspend fun getAllUsers(): List<User>

    @GET("api/users/count")
    suspend fun countUsers(): Long

    @GET("api/users/{id}")
    suspend fun getUserById(@Path("id") id: Long): User?

    // MÉTODOS DE PRODUCTO
    @POST("api/products")
    suspend fun saveOrUpdateProduct(@Body product: Product): Product

    @GET("api/products")
    suspend fun getAllProducts(): List<Product>

    @GET("api/products/count")
    suspend fun countProducts(): Long

    @GET("api/products/{id}")
    suspend fun getProductById(@Path("id") id: Long): Product?

    // MÉTODOS DE CARRITO
    @GET("api/carrito")
    suspend fun getAllItemsInCart(): List<ItemCarrito>

    @POST("api/carrito/add")
    suspend fun addItemToCart(@Body payload: Map<String, Any>): ItemCarrito

    @GET("api/carrito/{userId}")
    suspend fun getCartDetailsByUserId(@Path("userId") userId: Long): List<ItemCarrito>

    @DELETE("api/carrito/clear/{userId}")
    suspend fun clearCart(@Path("userId") userId: Long)

    @DELETE("api/carrito/item/{itemCarritoId}")
    suspend fun removeItemFromCart(@Path("itemCarritoId") itemCarritoId: Long)

    @PUT("api/products/decrease-stock/{id}")
    suspend fun decreaseStock(@Path("id") id: Long, @Body request: StockDecreaseRequest)
}