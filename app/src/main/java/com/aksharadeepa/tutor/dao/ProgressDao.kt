package com.aksharadeepa.tutor.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aksharadeepa.tutor.models.Progress

@Dao
interface ProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(progress: Progress)

    @Query("SELECT COUNT(*) FROM progress WHERE completed = 1")
    fun completedCountLive(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM progress WHERE completed = 1 AND completedAt >= :start AND completedAt < :end")
    fun completedBetweenLive(start: Long, end: Long): LiveData<Int>
}
