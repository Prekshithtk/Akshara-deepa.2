package com.aksharadeepa.tutor.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = prefs.getBoolean("logged_in", false)
        set(value) = prefs.edit().putBoolean("logged_in", value).apply()

    var username: String?
        get() = prefs.getString("username", "Student")
        set(value) = prefs.edit().putString("username", value).apply()

    var isDarkMode: Boolean
        get() = prefs.getBoolean("dark_mode", false)
        set(value) = prefs.edit().putBoolean("dark_mode", value).apply()

    var isReminderEnabled: Boolean
        get() = prefs.getBoolean("reminder", false)
        set(value) = prefs.edit().putBoolean("reminder", value).apply()

    val streak: Int
        get() = prefs.getInt("streak", 0)

    val lastGoalDay: Long
        get() = prefs.getLong("last_goal_day", 0L)

    fun updateStreakIfNeeded(dayKey: Long) {
        val last = lastGoalDay
        if (last == dayKey) return
        val newStreak = if (last == dayKey - 1) streak + 1 else 1
        prefs.edit()
            .putLong("last_goal_day", dayKey)
            .putInt("streak", newStreak)
            .apply()
    }

    companion object {
        private const val PREFS = "akshara_prefs"
    }
}
