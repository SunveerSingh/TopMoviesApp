package io.github.sunveersingh.topmovies.model

import android.R
import android.app.*
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import io.github.sunveersingh.topmovies.home


class brodcast: BroadcastReceiver() {
    private val CHANNEL_ID = "io.github.sunveersingh.topmovies.channelId"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationIntent = Intent(context, home::class.java)

        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(home::class.java)
        stackBuilder.addNextIntent(notificationIntent)

        val pendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = Notification.Builder(context, CHANNEL_ID)

        val notification: Notification =
            builder.setContentTitle("Movie Reminder")
                .setContentText("This is an movie reminder")
                .setTicker("New Message Alert!")
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
                .setContentIntent(pendingIntent).build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "NotificationDemo",
                IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notification)
    }
}