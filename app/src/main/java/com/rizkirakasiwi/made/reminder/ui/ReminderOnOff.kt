package com.rizkirakasiwi.made.reminder.ui

import android.app.ActivityManager
import android.content.ContentValues
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.data.other.Reminder
import com.rizkirakasiwi.made.database.DatabaseHelper
import com.rizkirakasiwi.made.reminder.method.dailyReminder.DailyService
import com.rizkirakasiwi.made.reminder.method.releaseReminder.LoadReleaseData
import com.rizkirakasiwi.made.reminder.method.ReminderMethod
import com.rizkirakasiwi.made.reminder.method.dailyReminder.DailyReceiver
import com.rizkirakasiwi.made.reminder.method.releaseReminder.ReleaseReceiver
import com.rizkirakasiwi.made.reminder.method.releaseReminder.ReleaseService
import kotlinx.android.synthetic.main.reminder_on_off_fragment.*

class ReminderOnOff : Fragment() {

    companion object {
        fun newInstance() = ReminderOnOff()
    }

    private lateinit var viewModel: ReminderOnOffViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reminder_on_off_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ReminderOnOffViewModel::class.java)
        loadReminder()
    }

    private fun loadReminder(){
        val reminder = DatabaseHelper(context!!).loadReminder()
        viewModel.setReminder(reminder)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.setting)
        val mStartServiceIntent = Intent(this.context, DailyService::class.java)
        val mStartServiceRelease = Intent(this.context, ReleaseService::class.java)

        switch_daily_reminder.setOnClickListener {
            if(switch_daily_reminder.isChecked){
                val contenValues = ContentValues()
                contenValues.put(Reminder.REMINDER_DAILY,1)
                DatabaseHelper(context!!).updateReminder(contenValues)
                activity?.startService(mStartServiceIntent)
                Log.i("Reminder", "isChecked")
                loadReminder()
            }else{
                val contenValues = ContentValues()
                contenValues.put(Reminder.REMINDER_DAILY,0)
                DatabaseHelper(context!!).updateReminder(contenValues)
                Log.i("Reminder", "isNotChecked")
                activity?.stopService(mStartServiceIntent)
                loadReminder()
                ReminderMethod(context!!).stopAlarm(ReminderMethod.ALARM_REQUEST_DAILY_CODE,DailyReceiver::class.java)
            }
        }

        switch_release_reminder.setOnClickListener {
            if(switch_release_reminder.isChecked){
                val contenValues = ContentValues()
                contenValues.put(Reminder.RELEASE_REMINDER,1)
                DatabaseHelper(context!!).updateReminder(contenValues)
                activity?.startService(mStartServiceRelease)
                Log.i("Reminder", "isChecked")
                loadReminder()
            }else{
                val contenValues = ContentValues()
                contenValues.put(Reminder.RELEASE_REMINDER,0)
                DatabaseHelper(context!!).updateReminder(contenValues)
                Log.i("Reminder", "isNotChecked")
                activity?.stopService(mStartServiceRelease)
                loadReminder()
                ReminderMethod(context!!).stopAlarm(ReminderMethod.ALARM_REQUEST_RELEASE_CODE, ReleaseReceiver::class.java)
            }
        }


        viewModel.reminder.observe(this, Observer {
            Log.i("Reminder", "reminder database $it")
            if(!it.isNullOrEmpty()) {
                switch_release_reminder.isChecked = it[0].releaseReminder != 0
                switch_daily_reminder.isChecked = it[0].reminderDaily != 0
            }
        })


        if(isMyServiceRunning(DailyService::class.java)) {
            Log.i("Reminder", "Service Running")

        }
    }


    private fun isMyServiceRunning(serviceClass : Class<*> ) : Boolean{
        val manager = activity?.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

}
