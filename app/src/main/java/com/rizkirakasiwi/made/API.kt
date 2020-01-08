package com.rizkirakasiwi.made

import com.google.gson.Gson
import com.rizkirakasiwi.made.fragment.data.genre.DataGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

object API {

    private val api_key = "4a286864a1858629d01785f0a55d4f90"

    fun movieUrl() = "https://api.themoviedb.org/3/discover/movie?api_key=$api_key&language=en-US"
    fun genreMovieUrl() = "https://api.themoviedb.org/3/genre/movie/list?api_key=$api_key&language=en-US"
    fun genreTvUrl() = "https://api.themoviedb.org/3/genre/tv/list?api_key=$api_key&language=en-US"
    fun TvShowUrl() = "https://api.themoviedb.org/3/discover/tv?api_key=$api_key&language=en-US"
    fun LanguageUrl() = "https://api.themoviedb.org/3/configuration/languages?api_key=$api_key"

    fun poster(poster_path:String, poster_size:String = "w92")
            = "https://image.tmdb.org/t/p/$poster_size$poster_path"


    fun <T>getData(url:String, objectClass:Class<T>) : T{
        val request = Request.Builder()
            .url(url)
            .build()

        val json =  OkHttpClient()
            .newCall(request)
            .execute().body!!.string()

        return Gson().fromJson(json, objectClass)
    }

    suspend fun getGenre(url: String) = withContext(Dispatchers.IO){
        val genreList = API.getData(url, DataGenre::class.java)
        val genreMap = hashMapOf<Int, String>()
        genreList.genres.forEach {
            genreMap[it.id] = it.name
        }

        return@withContext genreMap
    }

}
