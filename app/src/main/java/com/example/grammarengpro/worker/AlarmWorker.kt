package com.example.grammarengpro.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.grammarengpro.MainApp
import com.example.grammarengpro.R
import com.example.grammarengpro.ui.main.MainActivity
import com.example.grammarengpro.ui.splash.SplashScreenActivity
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private lateinit var mNotifyManager: NotificationManager

    override fun doWork(): Result {
        sendNotification(applicationContext)
        return Result.success()
    }

    private fun sendNotification(context: Context?) {
        val updateIntent = Intent(ACTION_UPDATE_NOTIFICATION)
        val updatePendingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_ID,
            updateIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val notifyBuilder = getNotificationBuilder(context)
//        notifyBuilder.addAction(
//            R.drawable.ic_baseline_notifications_active_24,
//            "Update Notification",
//            updatePendingIntent
//        )
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build())
    }

    private fun getNotificationBuilder(context: Context?): NotificationCompat.Builder {
        createNotificationChannel(context)
        val notificationIntent = Intent(context, MainApp.getCurrentActivity().javaClass).apply {
            this.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val notificationPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Builder(context!!, PRIMARY_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.title_notifi))
            .setContentText(context.getText(R.string.lable_notifi))
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentIntent(notificationPendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
    }

    private fun createNotificationChannel(context: Context?) {
        mNotifyManager =
            context?.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Demo Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notification from Demo"
            mNotifyManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        val TAG: String = AlarmWorker::class.java.simpleName.toString()
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        const val NOTIFICATION_ID = 0
        const val ACTION_UPDATE_NOTIFICATION =
            "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION"

        fun stopScheduleReminder(context: Context?) {
            WorkManager.getInstance(context!!).cancelAllWorkByTag(TAG)
        }

        fun startScheduleReminder(
            context: Context?,
            checkAlarm: IntArray,
            currentTime: Long,
            setupTime: Long
        ) {
            val calendar = Calendar.getInstance()
            val dateNow = Date()
            calendar.time = dateNow
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            var durationTime = 0L

            if (checkAlarm[dayOfWeek - 1] == 99) {
                durationTime = if (currentTime < setupTime) {
                    setupTime - currentTime
                } else {
                    currentTime + 86400000 - (currentTime - setupTime)
                }
            }

            val remindBuilder =
                PeriodicWorkRequest.Builder(AlarmWorker::class.java, 1, TimeUnit.DAYS)
                    .setInitialDelay(durationTime, TimeUnit.MILLISECONDS)
                    .addTag(TAG)
                    .build()

            WorkManager.getInstance(context!!)
                .enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.REPLACE, remindBuilder)
        }
    }
}