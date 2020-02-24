package com.example.favorite.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.rizkirakasiwi.made.fragment.ui.favorite.favoriteMovies.FavoriteMovies
import com.rizkirakasiwi.made.fragment.ui.favorite.favoriteTvShow.FavoriteTvShow


class ViewPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    private val favorite = listOf(
        FavoriteMovies(),
        FavoriteTvShow()
    )



    override fun getCount(): Int = favorite.count()

    override fun getItem(position: Int) = favorite[position]
}
