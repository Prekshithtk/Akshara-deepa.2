package com.aksharadeepa.tutor.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "quiz_results",
    foreignKeys = [
        ForeignKey(
            entity = Chapter::class,
            parentColumns = ["id"],
            childColumns = ["chapterId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("chapterId")]
)
data class QuizResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val chapterId: Int,
    val subjectId: Int,
    val score: Int,
    val total: Int,
    val takenAt: Long
)
