package com.aksharadeepa.tutor.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.aksharadeepa.tutor.AksharaApp
import com.aksharadeepa.tutor.R
import com.aksharadeepa.tutor.activities.LoginActivity

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val launch = Intent(context, LoginActivity::class.java)
        var flags = PendingIntent.FLAG_UPDATE_CURRENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags = flags or PendingIntent.FLAG_IMMUTABLE
        }
        val contentIntent = PendingIntent.getActivity(context, 7, launch, flags)
        val builder = NotificationCompat.Builder(context, AksharaApp.CHANNEL_STUDY)
            .setSmallIcon(R.drawable.ic_lamp)
            .setContentTitle("Akshara-Deepa Tutor")
            .setContentText("Complete at least one topic today.")
            .setAutoCancel(true)
            .setContentIntent(contentIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        try {
            NotificationManagerCompat.from(context).notify(1001, builder.build())
        } catch (ignored: SecurityException) {
            // Notification permission may be denied on Android 13+.
        }
    }
}
