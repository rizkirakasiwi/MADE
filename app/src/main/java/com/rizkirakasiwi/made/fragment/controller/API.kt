package com.rizkirakasiwi.made.fragment.controller

import com.google.gson.Gson
import com.rizkirakasiwi.made.BuildConfig
import com.rizkirakasiwi.made.fragment.data.genre.DataGenre
import com.rizkirakasiwi.made.fragment.data.language.DataLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

object API {

    private val api_key = BuildConfig.API_KEY

    fun movieUrl(language: String) =
        "https://api.themoviedb.org/3/discover/movie?api_key=$api_key&language=$language"

    fun genreMovieUrl(language: String) =
        "https://api.themoviedb.org/3/genre/movie/list?api_key=$api_key&language=$language"

    fun genreTvUrl(language: String) =
        "https://api.themoviedb.org/3/genre/tv/list?api_key=$api_key&language=$language"

    fun TvShowUrl(language: String) =
        "https://api.themoviedb.org/3/discover/tv?api_key=$api_key&language=$language"

    fun LanguageUrl() = "https://api.themoviedb.org/3/configuration/languages?api_key=$api_key"

    fun poster(poster_path: String, poster_size: String = "w154") =
        "https://image.tmdb.org/t/p/$poster_size$poster_path"

    fun searchMovieUrl(language: String, query: String?) =
        "https://api.themoviedb.org/3/search/movie?api_key=$api_key&language=$language&query=$query"

    fun searchTvShowUrl(language: String, query: String?) =
        "https://api.themoviedb.org/3/search/tv?api_key=$api_key&language=$language&query=$query"


    fun <T> getData(url: String, objectClass: Class<T>): T {
        val request = Request.Builder()
            .url(url)
            .build()

        val json = OkHttpClient()
            .newCall(request)
            .execute().body!!.string()

        return Gson().fromJson(json, objectClass)
    }

    suspend fun getGenre(url: String) = withContext(Dispatchers.IO) {
        val genreList =
            getData(url, DataGenre::class.java)
        val genreMap = hashMapOf<Int, String>()
        genreList.genres.forEach {
            genreMap[it.id] = it.name
        }

        return@withContext genreMap
    }

    suspend fun getLanguage(url: String) = withContext(Dispatchers.IO) {
        val languageList = getData(
            url,
            Array<DataLanguage>::class.java
        )
        val languageMap = hashMapOf<String, String>()
        languageList.forEach {
            languageMap[it.iso_639_1] = it.english_name
        }

        return@withContext languageMap
    }


}
