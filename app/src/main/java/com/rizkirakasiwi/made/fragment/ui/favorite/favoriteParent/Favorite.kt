package com.rizkirakasiwi.made.fragment.ui.favorite.favoriteParent

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.ViewPagerAdapter
import kotlinx.android.synthetic.main.favorite_fragment.*
import kotlinx.android.synthetic.main.fragment_home_main.*

class Favorite : Fragment() {

    companion object {
        fun newInstance() =
            Favorite()
    }

    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(childFragmentManager, isFavorite = true)
        viewPager_Favorite.adapter = adapter
        viewPager_Favorite.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_Favorite))
        tab_Favorite.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager_Favorite.currentItem = p0!!.position
            }

        })
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.favorite)
    }

}
