package com.rizkirakasiwi.made.fragment.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.model.FavoriteMoviesViewModel

class FavoriteMovies : Fragment() {

    companion object {
        fun newInstance() = FavoriteMovies()
    }

    private lateinit var viewModel: FavoriteMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoriteMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
