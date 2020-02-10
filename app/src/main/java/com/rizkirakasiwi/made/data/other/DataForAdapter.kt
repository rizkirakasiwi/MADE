package com.rizkirakasiwi.made.data.other

import com.rizkirakasiwi.made.data.movie.DataMovie
import com.rizkirakasiwi.made.data.tvShow.DataTv

data class DataForAdapter(
    val movie: DataMovie? = null,
    val tvshow:DataTv? = null,
    val genre:HashMap<Int,String>?,
    val language:HashMap<String, String>?
)