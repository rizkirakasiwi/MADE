package com.rizkirakasiwi.made.fragment.model

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.data.MovieData

class TvViewModel : ViewModel() {
    private val _listTv = MutableLiveData<List<MovieData>>()
    val listTv : LiveData<List<MovieData>> get() = _listTv

    private val _listImage = MutableLiveData<List<Int>>()
    val listImage : LiveData<List<Int>> get() = _listImage

    private val tvArray = listOf(
        R.array.glass,
        R.array.hunterkiller,
        R.array.marrypopins,
        R.array.mortalengine,
        R.array.preman,
        R.array.robinhood,
        R.array.spiderman,
        R.array.thegirl,
        R.array.themule,
        R.array.venom
    )

    fun tvData(view: View){
        val tvList = arrayListOf<MovieData>()
        for (i in tvArray) {
            val tv = view.resources.getStringArray(i)
            tvList.add(
                MovieData(
                    tv[0],
                    tv[1],
                    tv[2],
                    tv[3],
                    tv[4],
                    tv[5],
                    tv[6]
                )
            )
        }

        _listTv.value = tvList
    }

    fun imageData(view: View){
        val imageLocation = R.array.poster_tv
        val imageArray = view.resources.obtainTypedArray(imageLocation)
        val imageList = arrayListOf<Int>()
        for (i in 0..imageArray.length()){
            imageList.add(imageArray.getResourceId(i, -1))
        }
        _listImage.value = imageList
    }
}
