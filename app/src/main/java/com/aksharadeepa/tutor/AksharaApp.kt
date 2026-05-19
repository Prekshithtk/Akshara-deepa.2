package com.aksharadeepa.tutor

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.aksharadeepa.tutor.utils.PreferenceManager
import com.aksharadeepa.tutor.utils.ReminderScheduler

class AksharaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val prefs = PreferenceManager(this)
        AppCompatDelegate.setDefaultNightMode(
            if (prefs.isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        createNotificationChannel()
        if (prefs.isReminderEnabled) {
            ReminderScheduler.schedule(this)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_STUDY,
                "Study reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Daily offline reminder to complete one topic."
            }
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_STUDY = "study_reminders"
    }
}
