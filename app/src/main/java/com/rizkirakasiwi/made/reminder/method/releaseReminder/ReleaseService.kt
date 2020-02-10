package com.rizkirakasiwi.made.reminder.method.releaseReminder

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.data.movie.MovieResult
import com.rizkirakasiwi.made.data.tvShow.TvResult
import com.rizkirakasiwi.made.reminder.method.ReminderMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ReleaseService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRunningTimeReminderRelease(8,0)
        return START_STICKY
    }


    suspend fun getReleaseMovieData() = withContext(Dispatchers.IO){
        return@withContext LoadReleaseData.getMovie()
    }

    suspend fun getReleaseTvShow() = withContext(Dispatchers.IO){
        return@withContext LoadReleaseData.getTvShow()
    }


    @SuppressLint("SimpleDateFormat")
    private fun isRunningTimeReminderRelease(hour:Int, minute:Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            var time: String =  current.format(formatter)
            val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if(timeArray[0].toInt() > hour && timeArray[1].toInt() > minute){
                Log.i("Reminder", "Time passed release")
            }else{
                GlobalScope.launch(Dispatchers.IO) {
                    val listRelease = arrayListOf<MovieResult>()
                    getReleaseMovieData().results.forEach {
                        listRelease.add(it)
                    }

                    val listReleaseTv = arrayListOf<TvResult>()
                    getReleaseTvShow().results.forEach {
                        listReleaseTv.add(it)
                    }

                    Log.i("Reminder", getReleaseMovieData().toString())
                    ReminderMethod(this@ReleaseService)
                        .startAlarm(
                            hour,
                            minute,
                            ReminderMethod.ALARM_REQUEST_RELEASE_CODE,
                            ReleaseReceiver::class.java,
                            listRelease,
                            listReleaseTv
                        )
                }
                Log.i("Reminder", "Time before release")
            }
        } else {
            var date = Date();
            val formatter = SimpleDateFormat("HH:mm:ss")
            val time: String = formatter.format(date)
            val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if(timeArray[0].toInt() > hour && timeArray[1].toInt() > minute){
                Log.i("Reminder", "time passed")
            }else{
                GlobalScope.launch(Dispatchers.IO) {
                    val listRelease = arrayListOf<MovieResult>()
                    getReleaseMovieData().results.forEach {
                        listRelease.add(it)
                    }

                    val listReleaseTv = arrayListOf<TvResult>()
                    getReleaseTvShow().results.forEach {
                        listReleaseTv.add(it)
                    }
                    Log.i("Reminder", getReleaseMovieData().toString())
                    ReminderMethod(this@ReleaseService)
                        .startAlarm(
                            hour,
                            minute,
                            ReminderMethod.ALARM_REQUEST_RELEASE_CODE,
                            ReleaseReceiver::class.java,
                            listRelease,
                            listReleaseTv
                        )
                }
                Log.i("Reminder", "Time before")
            }
        }
    }

}

