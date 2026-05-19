package com.aksharadeepa.tutor.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "progress",
    foreignKeys = [
        ForeignKey(
            entity = Chapter::class,
            parentColumns = ["id"],
            childColumns = ["chapterId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Progress(
    @PrimaryKey
    val chapterId: Int,
    val completed: Boolean,
    val completedAt: Long
)
