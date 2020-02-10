package com.rizkirakasiwi.made.activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.data.other.Reminder
import com.rizkirakasiwi.made.database.DatabaseHelper
import com.rizkirakasiwi.made.reminder.method.dailyReminder.DailyService
import com.rizkirakasiwi.made.reminder.method.ReminderMethod
import com.rizkirakasiwi.made.reminder.method.releaseReminder.ReleaseReceiver
import com.rizkirakasiwi.made.reminder.method.releaseReminder.ReleaseService

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0.0f
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(this, navController)



        val contentValues = ContentValues()
        val mStartServiceIntent = Intent(this, DailyService::class.java)
        val mStartServiceRelease = Intent(this, ReleaseService::class.java)
        val checkingReminder = DatabaseHelper(this).loadReminder()
        if (checkingReminder.isNullOrEmpty()){
            contentValues.put(Reminder.ID, 1)
            contentValues.put(Reminder.RELEASE_REMINDER, 1)
            contentValues.put(Reminder.REMINDER_DAILY, 1)
            DatabaseHelper(this).insertReminder(contentValues)
            Log.i("Reminder", "inserted content values")
            startService(mStartServiceIntent)
            startService(mStartServiceRelease)
        }

    }

    override fun onResume() {
        super.onResume()
        ReminderMethod(this).showNotification(isCancel = true)
//        ReleaseReceiver().showNotificationGroupMovie(true, null,this)
    }



    override fun onSupportNavigateUp() = NavigationUI.navigateUp(navController,null)



}
