package com.rizkirakasiwi.made.fragment.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.model.DetailViewModel
import com.rizkirakasiwi.made.fragment.data.MovieData

class Detail : Fragment() {

    companion object {
        fun newInstance() = Detail()
        val CODE = "MovieData"
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        val movieData = arguments?.getParcelable<MovieData>(CODE)
        viewModel.setMovieData(movieData)
    }

    override fun onResume() {
        super.onResume()

    }

}