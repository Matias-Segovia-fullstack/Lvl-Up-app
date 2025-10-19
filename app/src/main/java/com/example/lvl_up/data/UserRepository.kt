package com.example.lvl_up.data

import kotlinx.coroutines.flow.Flow

class UserRepository (private val userDAO: UserDAO) {

    val allUsers: Flow<List<User>> = userDAO.getAllUsers()


    suspend fun insert(user: User) {
        userDAO.insertUser(user)
    }

    suspend fun update(user: User) {
        userDAO.updateUser(user)
    }

    suspend fun getUserById(id: Int): User? {
        return userDAO.getUserById(id)
    }




}