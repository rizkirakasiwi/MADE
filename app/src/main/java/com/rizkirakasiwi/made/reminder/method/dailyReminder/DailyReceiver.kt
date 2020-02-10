package com.rizkirakasiwi.made.reminder.method.dailyReminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.rizkirakasiwi.made.reminder.method.ReminderMethod

class DailyReceiver : BroadcastReceiver() {

    private val TAG = "Reminder"

    override fun onReceive(context: Context, intent: Intent) {
        val isTimeForNotif = intent.getBooleanExtra(ReminderMethod.ALARM_EXTRA, false)
        if(isTimeForNotif){
            ReminderMethod(context)
                .showNotification(ReminderMethod.NOTIF_DAILY_REMINDER,false)
            Log.i(TAG, "Start Alarm set up daily")
        }
    }
}
