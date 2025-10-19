package com.example.lvl_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lvl_up.data.ProductRepository
import com.example.lvl_up.data.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AdminViewModel(
    productRepository: ProductRepository,
    userRepository: UserRepository
) : ViewModel() {

    // Estado del conteo de productos
    val productCountState: StateFlow<Int> = productRepository.productCount
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // Estado del conteo de usuarios
    val userCountState: StateFlow<Int> = userRepository.userCount
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
}