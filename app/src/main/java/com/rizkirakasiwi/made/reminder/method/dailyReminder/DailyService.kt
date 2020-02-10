package com.rizkirakasiwi.made.reminder.method.dailyReminder

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.rizkirakasiwi.made.reminder.method.ReminderMethod
import com.rizkirakasiwi.made.reminder.method.releaseReminder.ReleaseReceiver
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DailyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRunningTimeReminderDaily(7, 0)
        return START_STICKY
    }

    @SuppressLint("SimpleDateFormat")
    private fun isRunningTimeReminderDaily(hour:Int, minute:Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            var time: String =  current.format(formatter)
            val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if(timeArray[0].toInt() > hour && timeArray[1].toInt() > minute){
                Log.i("Reminder", "Time passed")
            }else{
                ReminderMethod(this)
                    .startAlarm(hour, minute, ReminderMethod.ALARM_REQUEST_DAILY_CODE, DailyReceiver::class.java)
                Log.i("Reminder", "Time before")
            }
        } else {
            var date = Date();
            val formatter = SimpleDateFormat("HH:mm:ss")
            val time: String = formatter.format(date)
            val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if(timeArray[0].toInt() > hour && timeArray[1].toInt() > minute){
                Log.i("Reminder", "time passed")
            }else{
                ReminderMethod(this)
                    .startAlarm(hour, minute, ReminderMethod.ALARM_REQUEST_DAILY_CODE,
                        DailyReceiver::class.java)
                Log.i("Reminder", "Time before")
            }
        }
    }
}
