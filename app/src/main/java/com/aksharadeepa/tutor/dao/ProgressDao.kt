package com.aksharadeepa.tutor.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aksharadeepa.tutor.models.Progress;

@Dao
public interface ProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(Progress progress);

    @Query("SELECT COUNT(*) FROM progress WHERE completed = 1")
    LiveData<Integer> completedCountLive();

    @Query("SELECT COUNT(*) FROM progress WHERE completed = 1 AND completedAt >= :start AND completedAt < :end")
    LiveData<Integer> completedBetweenLive(long start, long end);
}
