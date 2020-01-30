package com.rizkirakasiwi.made.fragment.ui.favorite.favoriteTvShow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkirakasiwi.made.fragment.data.FavoriteDb

class FavoriteTvShowViewModel : ViewModel() {
    private val _favoriteData = MutableLiveData<List<FavoriteDb>>()
    val favoriteData : LiveData<List<FavoriteDb>> get() = _favoriteData

    fun setFavorite(favoriteDb: List<FavoriteDb>){
        _favoriteData.value = favoriteDb
    }
}
