package com.rizkirakasiwi.made.reminder.method.releaseReminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.activity.MainActivity
import com.rizkirakasiwi.made.data.movie.DataMovie
import com.rizkirakasiwi.made.data.movie.MovieResult
import com.rizkirakasiwi.made.data.tvShow.TvResult
import com.rizkirakasiwi.made.reminder.method.DataClassReminder
import com.rizkirakasiwi.made.reminder.method.ReminderMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReleaseReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.getBundleExtra(ReminderMethod.ALARM_DATA_EXTRA)
        val data = bundle.getParcelable<DataClassReminder>(ReminderMethod.ALARM_DATA_EXTRA_BUNDLE)

        if (!data?.movie.isNullOrEmpty()){
            ReminderMethod(context).notificationRelease(context,data?.movie!!)
        }

        if (!data?.tvShow.isNullOrEmpty()){
            ReminderMethod(context).notificationReleaseTv(context,data?.tvShow!!)
        }
    }





}
