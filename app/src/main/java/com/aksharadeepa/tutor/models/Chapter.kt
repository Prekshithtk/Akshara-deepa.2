package com.aksharadeepa.tutor.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "chapters",
        foreignKeys = @ForeignKey(entity = Subject.class, parentColumns = "id", childColumns = "subjectId", onDelete = ForeignKey.CASCADE),
        indices = @Index("subjectId"))
public class Chapter {
    @PrimaryKey
    public int id;
    public int subjectId;
    public String title;
    public int position;

    public Chapter(int id, int subjectId, String title, int position) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.position = position;
    }
}
