package com.rizkirakasiwi.made.fragment.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkirakasiwi.made.fragment.controller.API
import com.rizkirakasiwi.made.fragment.data.other.DataForAdapter
import com.rizkirakasiwi.made.fragment.data.movie.DataMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieViewModel : ViewModel() {
    private val _dataForAdapter = MutableLiveData<DataForAdapter>()
    val dataForAdapter: LiveData<DataForAdapter> get() = _dataForAdapter

    suspend fun getMovieData(language: String) = withContext(Dispatchers.IO) {
        return@withContext API.getData(
            API.movieUrl(language),
            DataMovie::class.java
        )
    }


    fun setData(dataForAdapter: DataForAdapter) {
        _dataForAdapter.value = dataForAdapter
    }
}
