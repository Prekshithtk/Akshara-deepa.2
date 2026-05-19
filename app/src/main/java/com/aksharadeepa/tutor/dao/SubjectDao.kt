package com.aksharadeepa.tutor.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aksharadeepa.tutor.models.Subject
import com.aksharadeepa.tutor.models.SubjectMastery
import com.aksharadeepa.tutor.models.SubjectProgress

@Dao
interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(subjects: List<Subject>)

    @Query("SELECT * FROM subjects ORDER BY id")
    fun getAll(): LiveData<List<Subject>>

    @Query("SELECT COUNT(*) FROM subjects")
    suspend fun count(): Int

    @Query("SELECT s.id AS subjectId, s.name AS subjectName, COUNT(c.id) AS totalChapters, SUM(CASE WHEN p.completed = 1 THEN 1 ELSE 0 END) AS completedChapters FROM subjects s LEFT JOIN chapters c ON c.subjectId = s.id LEFT JOIN progress p ON p.chapterId = c.id GROUP BY s.id ORDER BY s.id")
    fun progressLive(): LiveData<List<SubjectProgress>>

    @Query("SELECT s.id AS subjectId, s.name AS subjectName, CASE WHEN SUM(q.total) IS NULL THEN 0 ELSE (SUM(q.score) * 100.0 / SUM(q.total)) END AS mastery FROM subjects s LEFT JOIN quiz_results q ON q.subjectId = s.id GROUP BY s.id ORDER BY s.id")
    fun masteryLive(): LiveData<List<SubjectMastery>>
}
