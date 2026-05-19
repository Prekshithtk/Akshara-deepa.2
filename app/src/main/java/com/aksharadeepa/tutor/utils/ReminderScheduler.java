package com.aksharadeepa.tutor.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.aksharadeepa.tutor.receivers.ReminderReceiver;

import java.util.Calendar;

public final class ReminderScheduler {
    private ReminderScheduler() {}

    public static void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = pendingIntent(context);
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, 19);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        if (time.getTimeInMillis() <= System.currentTimeMillis()) {
            time.add(Calendar.DAY_OF_YEAR, 1);
        }
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void cancel(Context context) {
        ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).cancel(pendingIntent(context));
    }

    private static PendingIntent pendingIntent(Context context) {
        Intent intent = new Intent(context, ReminderReceiver.class);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        return PendingIntent.getBroadcast(context, 42, intent, flags);
    }
}
