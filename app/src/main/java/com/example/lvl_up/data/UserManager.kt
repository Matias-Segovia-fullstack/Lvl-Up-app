package com.example.lvl_up.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserManager {
    private val _currentUserId = MutableStateFlow<Int?>(null)

    val currentUserIdFlow: StateFlow<Int?> = _currentUserId.asStateFlow()

    var currentUserId: Int?
        get() = _currentUserId.value
        set(value) {
            _currentUserId.value = value
        }

    var currentUserName: String? = null
    var currentUserEmail: String? = null
    var authToken: String? = null

    fun logout() {
        currentUserId = null
        currentUserName = null
        currentUserEmail = null
        authToken = null
    }
}