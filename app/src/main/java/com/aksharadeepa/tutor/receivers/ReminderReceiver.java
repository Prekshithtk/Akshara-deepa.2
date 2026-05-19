package com.aksharadeepa.tutor.receivers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.aksharadeepa.tutor.AksharaApp;
import com.aksharadeepa.tutor.R;
import com.aksharadeepa.tutor.activities.LoginActivity;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent launch = new Intent(context, LoginActivity.class);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent contentIntent = PendingIntent.getActivity(context, 7, launch, flags);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, AksharaApp.CHANNEL_STUDY)
                .setSmallIcon(R.drawable.ic_lamp)
                .setContentTitle("Akshara-Deepa Tutor")
                .setContentText("Complete at least one topic today.")
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        try {
            NotificationManagerCompat.from(context).notify(1001, builder.build());
        } catch (SecurityException ignored) {
            // Notification permission may be denied on Android 13+.
        }
    }
}
