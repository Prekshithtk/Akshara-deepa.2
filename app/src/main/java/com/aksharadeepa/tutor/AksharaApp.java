package com.aksharadeepa.tutor;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;

import com.aksharadeepa.tutor.utils.PreferenceManager;
import com.aksharadeepa.tutor.utils.ReminderScheduler;

public class AksharaApp extends Application {
    public static final String CHANNEL_STUDY = "study_reminders";

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager prefs = new PreferenceManager(this);
        AppCompatDelegate.setDefaultNightMode(prefs.isDarkMode()
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);
        createNotificationChannel();
        if (prefs.isReminderEnabled()) {
            ReminderScheduler.schedule(this);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_STUDY,
                    "Study reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Daily offline reminder to complete one topic.");
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
    }
}
