package com.rizkirakasiwi.made.fragment.controller

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.rizkirakasiwi.made.fragment.ui.Movie
import com.rizkirakasiwi.made.fragment.ui.Tv

class ViewPagerAdapter(fm:FragmentManager):FragmentStatePagerAdapter(fm) {

    private val fragment = listOf(Movie(), Tv())
    override fun getCount():Int = fragment.count()
    override fun getItem(position: Int) = fragment[position]
}
