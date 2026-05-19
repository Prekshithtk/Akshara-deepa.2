package com.aksharadeepa.tutor.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aksharadeepa.tutor.models.Subject;
import com.aksharadeepa.tutor.models.SubjectMastery;
import com.aksharadeepa.tutor.models.SubjectProgress;

import java.util.List;

@Dao
public interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Subject> subjects);

    @Query("SELECT * FROM subjects ORDER BY id")
    LiveData<List<Subject>> getAll();

    @Query("SELECT COUNT(*) FROM subjects")
    int count();

    @Query("SELECT s.id AS subjectId, s.name AS subjectName, COUNT(c.id) AS totalChapters, SUM(CASE WHEN p.completed = 1 THEN 1 ELSE 0 END) AS completedChapters FROM subjects s LEFT JOIN chapters c ON c.subjectId = s.id LEFT JOIN progress p ON p.chapterId = c.id GROUP BY s.id ORDER BY s.id")
    LiveData<List<SubjectProgress>> progressLive();

    @Query("SELECT s.id AS subjectId, s.name AS subjectName, CASE WHEN SUM(q.total) IS NULL THEN 0 ELSE (SUM(q.score) * 100.0 / SUM(q.total)) END AS mastery FROM subjects s LEFT JOIN quiz_results q ON q.subjectId = s.id GROUP BY s.id ORDER BY s.id")
    LiveData<List<SubjectMastery>> masteryLive();
}
