package com.example.lvl_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lvl_up.data.ProductRepository
import com.example.lvl_up.data.UserRepository

class AdminViewModelFactory(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
            return AdminViewModel(productRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}