package com.aksharadeepa.tutor.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "quiz_results",
        foreignKeys = @ForeignKey(entity = Chapter.class, parentColumns = "id", childColumns = "chapterId", onDelete = ForeignKey.CASCADE),
        indices = @Index("chapterId"))
public class QuizResult {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int chapterId;
    public int subjectId;
    public int score;
    public int total;
    public long takenAt;

    public QuizResult(int chapterId, int subjectId, int score, int total, long takenAt) {
        this.chapterId = chapterId;
        this.subjectId = subjectId;
        this.score = score;
        this.total = total;
        this.takenAt = takenAt;
    }
}
