package com.rizkirakasiwi.made.fragment.ui.search.searchParent

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_home_main.*
import kotlinx.android.synthetic.main.search_fragment.*

class Search : Fragment() {

    companion object {
        fun newInstance() =
            Search()
    }

    private lateinit var viewModel: SearchViewModel
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Search"
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(childFragmentManager, isSearch = true)
        viewPager_search.adapter = adapter
        viewPager_search.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_search))
        tab_search.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager_search.currentItem = p0!!.position
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search,menu)
        val mActionMenuItem = menu.findItem(R.id.menu_search_menu)
        searchView = mActionMenuItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("Search", query!!)
                return true
            }
        })
    }



}
