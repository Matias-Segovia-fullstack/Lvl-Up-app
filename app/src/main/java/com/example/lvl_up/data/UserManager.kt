package com.example.lvl_up.data

object UserManager {
    var currentUserId: Int? = null
    var currentUserName: String? = null
    var currentUserEmail: String? = null

    fun logout() {
        currentUserId = null
        currentUserName = null
        currentUserEmail = null
    }
}