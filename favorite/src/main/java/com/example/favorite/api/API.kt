package com.example.favorite.api

object API {


    fun poster(poster_path: String, poster_size: String = "w154") =
        "https://image.tmdb.org/t/p/$poster_size$poster_path"


}
