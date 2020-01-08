package com.rizkirakasiwi.made.fragment.data.other

import com.rizkirakasiwi.made.fragment.data.movie.DataMovie
import com.rizkirakasiwi.made.fragment.data.tvShow.DataTv

data class DataForAdapter(val movie: DataMovie? = null, val tvshow:DataTv? = null, val genre:HashMap<Int,String>)