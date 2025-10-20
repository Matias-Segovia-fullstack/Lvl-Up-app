package com.example.lvl_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lvl_up.data.CarritoRepository


class CarritoViewModelFactory(private val repository: CarritoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarritoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarritoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
