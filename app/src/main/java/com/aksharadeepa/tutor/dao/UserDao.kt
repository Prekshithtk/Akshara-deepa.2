package com.aksharadeepa.tutor.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aksharadeepa.tutor.models.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun findByUsername(username: String): User?

    @Query("SELECT COUNT(*) FROM users")
    suspend fun count(): Int
}
