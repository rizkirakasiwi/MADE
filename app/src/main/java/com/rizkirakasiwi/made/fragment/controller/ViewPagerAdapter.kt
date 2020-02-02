package com.rizkirakasiwi.made.fragment.controller

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.rizkirakasiwi.made.fragment.ui.favorite.favoriteMovies.FavoriteMovies
import com.rizkirakasiwi.made.fragment.ui.favorite.favoriteTvShow.FavoriteTvShow
import com.rizkirakasiwi.made.fragment.ui.home.movie.Movie
import com.rizkirakasiwi.made.fragment.ui.home.tvShow.Tv

class ViewPagerAdapter(
    fm: FragmentManager,
    val isFavorite: Boolean = false
) : FragmentStatePagerAdapter(fm) {

    private val fragment = listOf(
        Movie(),
        Tv()
    )
    private val favorite = listOf(
        FavoriteMovies(),
        FavoriteTvShow()
    )



    override fun getCount(): Int =
        if (isFavorite) favorite.count() else fragment.count()

    override fun getItem(position: Int) =
        if (isFavorite) favorite[position] else fragment[position]
}
