package com.aksharadeepa.tutor.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "progress",
        foreignKeys = @ForeignKey(entity = Chapter.class, parentColumns = "id", childColumns = "chapterId", onDelete = ForeignKey.CASCADE))
public class Progress {
    @PrimaryKey
    public int chapterId;
    public boolean completed;
    public long completedAt;

    public Progress(int chapterId, boolean completed, long completedAt) {
        this.chapterId = chapterId;
        this.completed = completed;
        this.completedAt = completedAt;
    }
}
