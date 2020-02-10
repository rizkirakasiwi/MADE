package com.rizkirakasiwi.made.data.other

import android.os.Parcelable

data class Reminder (val id:Int = 1, val reminderDaily:Int = 0, val releaseReminder:Int = 0){
    companion object{
        val ID = "id"
        val REMINDER_DAILY = "reminderDaily"
        val RELEASE_REMINDER = "releaseReminder"
    }
}