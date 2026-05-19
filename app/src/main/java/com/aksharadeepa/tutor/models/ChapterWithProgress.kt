package com.aksharadeepa.tutor.models

data class ChapterWithProgress(
    val id: Int,
    val subjectId: Int,
    val title: String,
    val position: Int,
    val completed: Boolean
)
