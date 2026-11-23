package com.example.activity_02.data

import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    val allUsers: Flow<List<User>> = userDao.getAllUsers()
    val userCount: Flow<Int> = userDao.getUserCount()

    suspend fun insert(user: User) {
        userDao.insertUser(user)
    }

    suspend fun update(user: User) {
        userDao.updateUser(user)
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    suspend fun updatePassword(userId: Int, newPassword: String) {
        userDao.updatePassword(userId, newPassword)
    }

    suspend fun delete(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun deleteAll() {
        userDao.deleteAllUsers()
    }
}
