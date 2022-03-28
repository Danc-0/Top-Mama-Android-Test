package com.example.topmama.utils.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.ListenableWorker.Result.success
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.topmama.R
import com.example.topmama.presentation.MainActivity
import com.example.topmama.utils.notify.work.NotifyWork.Companion.NOTIFICATION_ID

class NotifyWork(var context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val id = inputData.getLong(NOTIFICATION_ID, 0).toInt()
        showNotification(context, "Favourite Weather Reminder", "Hello its time, please check on the status of your favourite city weather information", id)
        return success()
    }

    private fun showNotification(context: Context, title: String, message: String, id: Int) {
        val mNotificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "1",
                "CH_ID",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Weather Alarm"
            mNotificationManager.createNotificationChannel(channel)
        }
        val mBuilder = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.mipmap.ic_launcher) // notification icon
            .setContentTitle(title) // title for notification
            .setContentText(message) // message for notification
            .setAutoCancel(true) // clear notification after click
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NOTIFICATION_ID, id)
        val pi = PendingIntent.getActivity(context, 0, intent, 0)
        mBuilder.setContentIntent(pi)
        mNotificationManager.notify(1001, mBuilder.build())
    }
}