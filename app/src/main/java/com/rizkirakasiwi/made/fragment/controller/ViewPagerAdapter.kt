package com.rizkirakasiwi.made.fragment.controller

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.rizkirakasiwi.made.fragment.ui.FavoriteMovies
import com.rizkirakasiwi.made.fragment.ui.FavoriteTvShow
import com.rizkirakasiwi.made.fragment.ui.Movie
import com.rizkirakasiwi.made.fragment.ui.Tv

class ViewPagerAdapter(fm:FragmentManager, val isFavorite:Boolean = false):FragmentStatePagerAdapter(fm) {

    private val fragment = listOf(Movie(), Tv())
    private val favorite = listOf(FavoriteMovies(), FavoriteTvShow())

    override fun getCount():Int = if(isFavorite) favorite.count() else fragment.count()
    override fun getItem(position: Int) = if(isFavorite) favorite[position] else fragment[position]
}
