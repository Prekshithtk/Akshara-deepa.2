package com.aksharadeepa.tutor.utils

import java.util.Calendar

object DateUtils {
    fun startOfToday(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    fun dayKey(): Long {
        return startOfToday() / 86_400_000L
    }
}
