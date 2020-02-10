package com.rizkirakasiwi.made.reminder.method

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.activity.MainActivity
import com.rizkirakasiwi.made.data.movie.MovieResult
import com.rizkirakasiwi.made.data.tvShow.TvResult
import java.util.*
import kotlin.collections.ArrayList

class ReminderMethod(private val context: Context) {
    private var idNotification = 0
    private var idNotificationTv = 0

    companion object {
        const val ALARM_EXTRA = "AlarmExtra"
        const val ALARM_DATA_EXTRA = "AlarmDataExtra"
        const val ALARM_DATA_EXTRA_BUNDLE = "AlarmDataExtraBundle"
        const val ALARM_REQUEST_DAILY_CODE = 0
        const val ALARM_REQUEST_RELEASE_CODE = 1
        const val NOTIFICATION_REQUEST_CODE = 222
        const val NOTIFICATION_REQUEST_CODETV = 223
        const val MAX_NOTIFICATION = 2
        const val NOTIF_DAILY_REMINDER = 10
        private const val CHANNEL_NAME = "release movie channel"
        const val GROUP_KEY = "group.key"
        const val GROUP_KEYTV = "group.key.tv"
    }

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent


    fun <T> startAlarm(
        hour: Int,
        minute: Int,
        alarmId: Int,
        objectClass: Class<T>,
        dataMovie: ArrayList<MovieResult>? = null,
        dataTv: ArrayList<TvResult>? = null
    ) {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, objectClass)
        intent.putExtra(ALARM_EXTRA, true)
        Log.i("Reminder", "Start Alarm with data $dataMovie")
        var data = DataClassReminder(dataMovie, dataTv)


        val bundle = Bundle()
        bundle.putParcelable(ALARM_DATA_EXTRA_BUNDLE, data)
        intent.putExtra(ALARM_DATA_EXTRA, bundle)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    }

    fun <T> stopAlarm(alarmId: Int, objectClass: Class<T>) {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, objectClass)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId, intent, 0
        )
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Log.i("Reminder", "Alarm stoped")
    }


    fun showNotification(
        notifId: Int = 0,
        isCancel: Boolean = false
    ) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "AlarmManager channel"

        Log.i("Reminder", "show notification")

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val notifPending: PendingIntent = PendingIntent.getActivity(context, notifId, intent, 0)

        val mTitle = context.getString(R.string.content_title)
        val mContentText = context.getString(R.string.content_text)

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentIntent(notifPending)
            .setContentTitle(mTitle)
            .setContentText(mContentText)
            .setSubText(context.getString(R.string.reminder))
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)

            notificationManagerCompat.createNotificationChannel(channel)
        }


        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)

        if (isCancel) {
            notificationManagerCompat.cancelAll()
        }

        Log.i("Reminder", "showNotification")
    }



    fun notificationRelease(context: Context, stackNotif:ArrayList<MovieResult>){
        stackNotif.forEach {
            idNotification += 1
        }
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_notifications)
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val mBuilder: NotificationCompat.Builder
        //Melakukan pengecekan jika idNotification lebih kecil dari Max Notif
        val CHANNEL_ID = "channel_01"
        if (idNotification < MAX_NOTIFICATION) {
            mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(stackNotif[idNotification].title)
                .setContentText(context.getString(R.string.release_today))
                .setSmallIcon(R.drawable.ic_notifications)
                .setLargeIcon(largeIcon)
                .setGroup(GROUP_KEY)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        } else {
            val inboxStyle = NotificationCompat.InboxStyle()
                .addLine(stackNotif[idNotification-1].title)
                .addLine(stackNotif[idNotification-2].title)
                .setBigContentTitle(context.getString(R.string.count_new_release, idNotification.toString()))
                .setSummaryText(context.getString(R.string.release_reminder))
            mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.count_new_release, idNotification.toString()))
                .setContentText(context.getString(R.string.content_text))
                .setSmallIcon(R.drawable.ic_notifications)
                .setGroup(GROUP_KEY)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)
                .setAutoCancel(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            val channel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }
        val notification = mBuilder.build()
        mNotificationManager.notify(idNotification, notification)
        Log.i("Reminder", "on notification release")
    }

    fun notificationReleaseTv(context: Context, stackNotif:ArrayList<TvResult>){
        stackNotif.forEach {
            idNotificationTv += 1
        }
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_notifications)
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODETV, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val mBuilder: NotificationCompat.Builder
        //Melakukan pengecekan jika idNotification lebih kecil dari Max Notif
        val CHANNEL_ID = "channel_01"
        if (idNotificationTv < MAX_NOTIFICATION) {
            mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(stackNotif[idNotificationTv].name)
                .setContentText(context.getString(R.string.release_today))
                .setSmallIcon(R.drawable.ic_notifications)
                .setLargeIcon(largeIcon)
                .setGroup(GROUP_KEYTV)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        } else {
            val inboxStyle = NotificationCompat.InboxStyle()
                .addLine(stackNotif[idNotificationTv-1].name)
                .addLine(stackNotif[idNotificationTv-2].name)
                .setBigContentTitle(context.getString(R.string.count_new_releaseTv, idNotificationTv.toString()))
                .setSummaryText(context.getString(R.string.release_reminder))
            mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.count_new_releaseTv, idNotificationTv.toString()))
                .setContentText(context.getString(R.string.content_text))
                .setSmallIcon(R.drawable.ic_notifications)
                .setGroup(GROUP_KEYTV)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)
                .setAutoCancel(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            val channel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }
        val notification = mBuilder.build()
        mNotificationManager.notify(idNotification, notification)
        Log.i("Reminder", "on notification release")
    }
}