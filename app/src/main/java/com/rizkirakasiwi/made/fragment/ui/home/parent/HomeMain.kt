package com.rizkirakasiwi.made.fragment.ui.home.parent


import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayout

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_home_main.*


class HomeMain : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.movie_catalogue)
        return inflater.inflate(R.layout.fragment_home_main, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(childFragmentManager)
        viewPager_homeMain.adapter = adapter
        viewPager_homeMain.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_homeMain))
        tab_homeMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager_homeMain.currentItem = p0!!.position
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_change_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.menu_favorite -> {
                view?.findNavController()?.navigate(R.id.action_homeMain_to_favorite)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
