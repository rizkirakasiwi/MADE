package com.rizkirakasiwi.made.fragment.ui.search.searchTvShow

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_home_main.*
import kotlinx.android.synthetic.main.search_fragment.*


class SearchTvShow : Fragment() {

    companion object {
        fun newInstance() =
            SearchTvShow()
    }

    private lateinit var viewModel: SearchTvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_tv_show_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchTvShowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
