package com.rizkirakasiwi.made.fragment.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkirakasiwi.made.API
import com.rizkirakasiwi.made.fragment.data.genre.DataGenre
import com.rizkirakasiwi.made.fragment.data.other.DataForAdapter
import com.rizkirakasiwi.made.fragment.data.tvShow.DataTv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TvViewModel : ViewModel() {
    private val _dataForAdapter = MutableLiveData<DataForAdapter>()
    val dataForAdapter : LiveData<DataForAdapter> get() = _dataForAdapter

    suspend fun getTvData() = withContext(Dispatchers.IO) {
        return@withContext API.getData(API.TvShowUrl(), DataTv::class.java)
    }


    fun setData(dataForAdapter: DataForAdapter){
        _dataForAdapter.value = dataForAdapter
    }
}
