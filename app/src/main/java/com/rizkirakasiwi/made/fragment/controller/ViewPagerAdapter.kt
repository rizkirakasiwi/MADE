package com.rizkirakasiwi.made.fragment.controller

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.rizkirakasiwi.made.fragment.ui.favorite.favoriteMovies.FavoriteMovies
import com.rizkirakasiwi.made.fragment.ui.favorite.favoriteTvShow.FavoriteTvShow
import com.rizkirakasiwi.made.fragment.ui.home.movie.Movie
import com.rizkirakasiwi.made.fragment.ui.home.tvShow.Tv
import com.rizkirakasiwi.made.fragment.ui.search.searchMovie.SearchMovie
import com.rizkirakasiwi.made.fragment.ui.search.searchTvShow.SearchTvShow

class ViewPagerAdapter(
    fm: FragmentManager,
    val isFavorite: Boolean = false,
    val isSearch: Boolean = false
) : FragmentStatePagerAdapter(fm) {

    private val fragment = listOf(
        Movie(),
        Tv()
    )
    private val favorite = listOf(
        FavoriteMovies(),
        FavoriteTvShow()
    )

    private val search = listOf(
        SearchMovie(),
        SearchTvShow()
    )

    override fun getCount(): Int =
        if (isFavorite) favorite.count() else if (isSearch) search.count() else fragment.count()

    override fun getItem(position: Int) =
        if (isFavorite) favorite[position] else if (isSearch) search[position] else fragment[position]
}
