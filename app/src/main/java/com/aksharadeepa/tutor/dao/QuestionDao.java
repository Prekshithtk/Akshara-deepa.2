package com.aksharadeepa.tutor.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aksharadeepa.tutor.models.QuizQuestion;

import java.util.List;

@Dao
public interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<QuizQuestion> questions);

    @Query("SELECT * FROM quiz_questions WHERE chapterId = :chapterId ORDER BY id LIMIT 5")
    List<QuizQuestion> getForChapter(int chapterId);

    @Query("SELECT COUNT(*) FROM quiz_questions")
    int count();
}
