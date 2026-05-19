package com.aksharadeepa.tutor.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "quiz_questions",
        foreignKeys = @ForeignKey(entity = Chapter.class, parentColumns = "id", childColumns = "chapterId", onDelete = ForeignKey.CASCADE),
        indices = @Index("chapterId"))
public class QuizQuestion {
    @PrimaryKey
    public int id;
    public int chapterId;
    public String question;
    public String optionA;
    public String optionB;
    public String optionC;
    public String optionD;
    public int correctIndex;
    public String explanation;

    public QuizQuestion(int id, int chapterId, String question, String optionA, String optionB, String optionC, String optionD, int correctIndex, String explanation) {
        this.id = id;
        this.chapterId = chapterId;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctIndex = correctIndex;
        this.explanation = explanation;
    }
}
