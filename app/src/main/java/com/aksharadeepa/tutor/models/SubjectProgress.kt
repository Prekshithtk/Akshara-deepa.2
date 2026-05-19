package com.aksharadeepa.tutor.models

import kotlin.math.roundToInt

data class SubjectProgress(
    val subjectId: Int,
    val subjectName: String,
    val totalChapters: Int,
    val completedChapters: Int
) {
    fun percent(): Int {
        return if (totalChapters == 0) 0 else (completedChapters * 100f / totalChapters).roundToInt()
    }
}
