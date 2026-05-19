package com.aksharadeepa.tutor.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aksharadeepa.tutor.models.QuizResult

@Dao
interface QuizResultDao {
    @Insert
    suspend fun insert(result: QuizResult)

    @Query("SELECT COUNT(*) FROM quiz_results")
    suspend fun count(): Int
}
