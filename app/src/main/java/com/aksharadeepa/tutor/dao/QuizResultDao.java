package com.aksharadeepa.tutor.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aksharadeepa.tutor.models.QuizResult;

@Dao
public interface QuizResultDao {
    @Insert
    void insert(QuizResult result);

    @Query("SELECT COUNT(*) FROM quiz_results")
    int count();
}
