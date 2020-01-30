package com.rizkirakasiwi.made.fragment.ui.search.searchMovie

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rizkirakasiwi.made.R

class SearchMovie : Fragment() {

    companion object {
        fun newInstance() = SearchMovie()
    }

    private lateinit var viewModel: SearchMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchMovieViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
