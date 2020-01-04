package com.rizkirakasiwi.made.fragment.model

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.data.MovieData

class MovieViewModel : ViewModel() {
    private val _listMovie = MutableLiveData<List<MovieData>>()
    val listMovie : LiveData<List<MovieData>> get() = _listMovie

    private val _listImage = MutableLiveData<List<Int>>()
    val listImage : LiveData<List<Int>> get() = _listImage

    private val movieArray = listOf(
        R.array.a_star,
        R.array.aquaman,
        R.array.avenger,
        R.array.bird_box,
        R.array.bohemian,
        R.array.bumblebee,
        R.array.creed,
        R.array.deadpool,
        R.array.dragon,
        R.array.dragonball
    )

    fun movieData(view:View){
        val movieList = arrayListOf<MovieData>()
        for (i in movieArray) {
            val movie = view.resources.getStringArray(i)
            movieList.add(
                MovieData(
                    movie[0],
                    movie[1],
                    movie[2],
                    movie[3],
                    movie[4],
                    movie[5],
                    movie[6]
                )
            )
        }

        _listMovie.value = movieList
    }

    fun imageData(view:View){
        val imageLocation = R.array.poster_movie
        val imageArray = view.resources.obtainTypedArray(imageLocation)
        val imageList = arrayListOf<Int>()
        for (i in 0..imageArray.length()){
            imageList.add(imageArray.getResourceId(i, -1))
        }
        _listImage.value = imageList
    }
}
