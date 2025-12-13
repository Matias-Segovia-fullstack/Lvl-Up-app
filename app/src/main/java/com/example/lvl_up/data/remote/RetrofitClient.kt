package com.example.lvl_up.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://192.168.1.124:8080/"//para emulador 10.0.2.2

object RetrofitClient {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = UserManager.authToken // Obtener el token del almacenamiento global

        // 1. Definir las rutas que deben ser excluidas del token (Login y Registro)
        val requestPath = originalRequest.url.encodedPath
        // El login es POST a /api/users/login
        val isLogin = requestPath.endsWith("/api/users/login") && originalRequest.method == "POST"
        // El registro es POST a /api/users
        val isRegister = requestPath.endsWith("/api/users") && originalRequest.method == "POST"

        val requestBuilder = originalRequest.newBuilder()

        // 2. Si hay token y NO es una ruta de exclusión, añadir el encabezado
        if (token != null && !isLogin && !isRegister) {
            // El formato es "Authorization: Bearer <token>"
            requestBuilder.header("Authorization", "Bearer $token")
        }

        chain.proceed(requestBuilder.build())
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor (authInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}