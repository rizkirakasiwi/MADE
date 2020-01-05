package com.rizkirakasiwi.made.fragment.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkirakasiwi.made.fragment.data.MovieData

class DetailViewModel : ViewModel() {
    private val _movieData = MutableLiveData<MovieData>()
    val movieData : LiveData<MovieData> get() = _movieData

    private val _imageData = MutableLiveData<Int>()
    val imageData : LiveData<Int> get() = _imageData

    fun setData(movie:MovieData?, image:Int?){
        _movieData.value = movie
        _imageData.value = image
    }

}
