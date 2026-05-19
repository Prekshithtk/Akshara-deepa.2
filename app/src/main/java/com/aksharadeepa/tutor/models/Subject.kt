package com.aksharadeepa.tutor.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey
    val id: Int,
    val name: String,
    val color: String
)
