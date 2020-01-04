package com.rizkirakasiwi.made.fragment.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rizkirakasiwi.made.fragment.model.MovieViewModel
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.MyAdapter
import com.rizkirakasiwi.made.fragment.controller.ShowLoading
import com.rizkirakasiwi.made.fragment.data.MovieData
import kotlinx.android.synthetic.main.movie_fragment.*


class Movie : Fragment() {

    companion object {
        fun newInstance() = Movie()
    }

    private lateinit var viewModel: MovieViewModel
    private lateinit var image:MutableList<Int>
    private lateinit var movie:MutableList<MovieData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        image = mutableListOf()
        movie = mutableListOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movieData(view)
        viewModel.imageData(view)
        ShowLoading.initilizeView(recycler_movie, progresbar_movie)
        viewModel.listImage.observe(this, Observer {
            image = it.toMutableList()
        })

        viewModel.listMovie.observe(this, Observer {
            movie = it.toMutableList()
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.movies)
        if(movie.isNullOrEmpty() && image.isNullOrEmpty()){
            ShowLoading.isLoad()
        }else {
            recycler_movie.adapter = MyAdapter(movie, image)
            ShowLoading.isDone()
        }
    }
}
