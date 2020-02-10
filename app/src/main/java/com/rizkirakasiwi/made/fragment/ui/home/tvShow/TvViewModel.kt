package com.rizkirakasiwi.made.fragment.ui.home.tvShow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkirakasiwi.made.api.API
import com.rizkirakasiwi.made.data.other.DataForAdapter
import com.rizkirakasiwi.made.data.tvShow.DataTv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TvViewModel : ViewModel() {
    private val _dataForAdapter = MutableLiveData<DataForAdapter>()
    val dataForAdapter: LiveData<DataForAdapter> get() = _dataForAdapter

    suspend fun getTvData(language: String) = withContext(Dispatchers.IO) {
        return@withContext API.getData(
            API.TvShowUrl(language),
            DataTv::class.java
        )
    }

    suspend fun getTvDataFromSearch(language: String, query:String?) = withContext(Dispatchers.IO) {
        return@withContext API.getData(
            API.searchTvShowUrl(language, query),
            DataTv::class.java
        )
    }

    fun setData(dataForAdapter: DataForAdapter) {
        _dataForAdapter.value = dataForAdapter
    }
}
