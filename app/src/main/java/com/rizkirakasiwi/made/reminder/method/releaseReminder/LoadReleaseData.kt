package com.rizkirakasiwi.made.reminder.method.releaseReminder

import com.rizkirakasiwi.made.api.API
import com.rizkirakasiwi.made.data.movie.DataMovie
import com.rizkirakasiwi.made.data.tvShow.DataTv

object LoadReleaseData {
    fun getMovie() = API.getData(API.releaseMovie(),DataMovie::class.java)
    fun getTvShow() = API.getData(API.releaseTvShow(), DataTv::class.java)
}