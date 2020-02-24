package com.example.favorite.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.favorite.R
import com.example.favorite.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = getString(R.string.favorite)
        val adapter =
            ViewPagerAdapter(supportFragmentManager)
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

}
