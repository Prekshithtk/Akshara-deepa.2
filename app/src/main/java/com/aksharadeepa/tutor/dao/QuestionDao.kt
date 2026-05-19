package com.aksharadeepa.tutor.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aksharadeepa.tutor.models.QuizQuestion

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuizQuestion>)

    @Query("SELECT * FROM quiz_questions WHERE chapterId = :chapterId ORDER BY id LIMIT 5")
    suspend fun getForChapter(chapterId: Int): List<QuizQuestion>

    @Query("SELECT COUNT(*) FROM quiz_questions")
    suspend fun count(): Int
}
