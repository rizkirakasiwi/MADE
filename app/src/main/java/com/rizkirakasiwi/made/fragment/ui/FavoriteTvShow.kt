package com.rizkirakasiwi.made.fragment.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.model.FavoriteTvShowViewModel

class FavoriteTvShow : Fragment() {

    companion object {
        fun newInstance() = FavoriteTvShow()
    }

    private lateinit var viewModel: FavoriteTvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_tv_show_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
