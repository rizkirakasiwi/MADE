package com.rizkirakasiwi.made.fragment.model

import android.content.ContentValues
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkirakasiwi.made.fragment.controller.GenerateToGenreName
import com.rizkirakasiwi.made.fragment.data.FavoriteDb
import com.rizkirakasiwi.made.fragment.data.other.DataDetail

class DetailViewModel : ViewModel() {
    private val _data = MutableLiveData<DataDetail>()
    val dataDetail: LiveData<DataDetail> get() = _data

    fun setMovieData(dataDetail: DataDetail) {
        _data.value = dataDetail
    }

}
