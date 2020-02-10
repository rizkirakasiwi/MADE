package com.rizkirakasiwi.made.reminder.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkirakasiwi.made.data.other.Reminder
import com.rizkirakasiwi.made.database.DatabaseHelper

class ReminderOnOffViewModel : ViewModel() {
    private val _reminder = MutableLiveData<MutableList<Reminder>>()
    val reminder : LiveData<MutableList<Reminder>> get() = _reminder

    fun setReminder(myreminder :MutableList<Reminder>){
        _reminder.value = myreminder
    }
}
