package com.example.activity_02.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("UPDATE users SET password = :newPassword WHERE id = :userId")
    suspend fun updatePassword(userId: Int, newPassword: String)

    @Delete
    suspend fun deleteUser(user: User)
}
