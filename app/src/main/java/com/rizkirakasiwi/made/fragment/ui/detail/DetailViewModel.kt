package com.rizkirakasiwi.made.fragment.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkirakasiwi.made.data.other.DataDetail

class DetailViewModel : ViewModel() {
    private val _data = MutableLiveData<DataDetail>()
    val dataDetail: LiveData<DataDetail> get() = _data

    fun setMovieData(dataDetail: DataDetail) {
        _data.value = dataDetail
    }

}
