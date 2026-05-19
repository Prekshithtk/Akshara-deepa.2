package com.aksharadeepa.tutor.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects")
public class Subject {
    @PrimaryKey
    public int id;
    public String name;
    public String color;

    public Subject(int id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}
