package com.example.lvl_up.data

import kotlinx.coroutines.flow.Flow

class UserRepository (private val userDAO: UserDAO) {

    val allUsers: Flow<List<User>> = userDAO.getAllUsers()

    val userCount: Flow<Int> = userDAO.getUserCount()

    suspend fun insert(user: User) {
        userDAO.insertUser(user)
    }

    suspend fun update(user: User) {
        userDAO.updateUser(user)
    }

    suspend fun getUserById(id: Int): User? {
        return userDAO.getUserById(id)
    }

    suspend fun findUserByCredentials(email: String, password: String): User? {
        return userDAO.findUserByCredentials(email, password)
    }


}