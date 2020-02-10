package com.rizkirakasiwi.made.reminder.method

import android.os.Parcelable
import com.rizkirakasiwi.made.data.movie.DataMovie
import com.rizkirakasiwi.made.data.movie.MovieResult
import com.rizkirakasiwi.made.data.tvShow.TvResult
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataClassReminder(val movie: ArrayList<MovieResult>?, val tvShow:ArrayList<TvResult>?):Parcelable