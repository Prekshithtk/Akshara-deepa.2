package com.aksharadeepa.tutor.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aksharadeepa.tutor.models.Chapter
import com.aksharadeepa.tutor.models.ChapterWithProgress

@Dao
interface ChapterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chapters: List<Chapter>)

    @Query("SELECT c.id, c.subjectId, c.title, c.position, CASE WHEN p.completed = 1 THEN 1 ELSE 0 END AS completed FROM chapters c LEFT JOIN progress p ON p.chapterId = c.id ORDER BY c.subjectId, c.position")
    fun chaptersWithProgressLive(): LiveData<List<ChapterWithProgress>>

    @Query("SELECT c.id, c.subjectId, c.title, c.position, CASE WHEN p.completed = 1 THEN 1 ELSE 0 END AS completed FROM chapters c LEFT JOIN progress p ON p.chapterId = c.id WHERE c.title LIKE '%' || :query || '%' ORDER BY c.subjectId, c.position")
    fun searchChapters(query: String): LiveData<List<ChapterWithProgress>>

    @Query("SELECT * FROM chapters WHERE id = :chapterId LIMIT 1")
    suspend fun getById(chapterId: Int): Chapter?
}
