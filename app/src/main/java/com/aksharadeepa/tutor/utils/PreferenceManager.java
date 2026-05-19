package com.aksharadeepa.tutor.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREFS = "akshara_prefs";
    private final SharedPreferences prefs;

    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean("logged_in", false);
    }

    public void setLoggedIn(boolean loggedIn) {
        prefs.edit().putBoolean("logged_in", loggedIn).apply();
    }

    public String getUsername() {
        return prefs.getString("username", "Student");
    }

    public void setUsername(String username) {
        prefs.edit().putString("username", username).apply();
    }

    public boolean isDarkMode() {
        return prefs.getBoolean("dark_mode", false);
    }

    public void setDarkMode(boolean enabled) {
        prefs.edit().putBoolean("dark_mode", enabled).apply();
    }

    public boolean isReminderEnabled() {
        return prefs.getBoolean("reminder", false);
    }

    public void setReminderEnabled(boolean enabled) {
        prefs.edit().putBoolean("reminder", enabled).apply();
    }

    public int getStreak() {
        return prefs.getInt("streak", 0);
    }

    public long getLastGoalDay() {
        return prefs.getLong("last_goal_day", 0L);
    }

    public void updateStreakIfNeeded(long dayKey) {
        long last = getLastGoalDay();
        if (last == dayKey) {
            return;
        }
        int streak = last == dayKey - 1 ? getStreak() + 1 : 1;
        prefs.edit().putLong("last_goal_day", dayKey).putInt("streak", streak).apply();
    }
}
