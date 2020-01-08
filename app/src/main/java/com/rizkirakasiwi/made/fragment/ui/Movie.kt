package com.rizkirakasiwi.made.fragment.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rizkirakasiwi.made.API
import com.rizkirakasiwi.made.fragment.model.MovieViewModel
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.MovieAdapter
import com.rizkirakasiwi.made.fragment.controller.ShowLoading
import com.rizkirakasiwi.made.fragment.data.other.DataForAdapter
import kotlinx.android.synthetic.main.movie_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Movie : Fragment() {

    companion object {
        fun newInstance() = Movie()
    }

    private lateinit var viewModel: MovieViewModel
    private val TAG = "Movie"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_fragment, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            val movie = viewModel.getMovieData()
            val genre = API.getGenre(API.genreMovieUrl())
            val dataForAdapter =
                DataForAdapter(movie = movie, genre = genre)
            viewModel.setData(dataForAdapter)
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ShowLoading.initilizeView(recycler_movie, progresbar_movie)
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.movies)
        viewModel.dataForAdapter.observe(this, Observer {
            if(!it.movie?.results.isNullOrEmpty() && !it.genre.isNullOrEmpty()) {
                ShowLoading.isDone()
                recycler_movie.adapter = MovieAdapter(it.movie!!, it.genre)
            }else{
                ShowLoading.isLoad()
            }
        })


    }
}
