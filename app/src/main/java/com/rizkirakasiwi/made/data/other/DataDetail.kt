package com.rizkirakasiwi.made.data.other

import com.rizkirakasiwi.made.data.movie.MovieResult
import com.rizkirakasiwi.made.data.tvShow.TvResult

data class DataDetail(
    val tv: TvResult? = null,
    val movie:MovieResult? = null,
    val favorite: FavoriteDb? = null,
    val genre:ArrayList<String>?,
    val language:String?
)