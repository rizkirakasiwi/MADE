package com.rizkirakasiwi.made.fragment.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rizkirakasiwi.made.fragment.controller.API
import com.rizkirakasiwi.made.fragment.model.MovieViewModel
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.MovieAdapter
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
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            val language = resources.getString(R.string.language)
            val movie = viewModel.getMovieData(language)
            val genre = API.getGenre(API.genreMovieUrl(language))
            val languageList = API.getLanguage(API.LanguageUrl())

            val dataForAdapter =
                DataForAdapter(movie = movie, genre = genre, language = languageList)
            viewModel.setData(dataForAdapter)
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.movies)
        viewModel.dataForAdapter.observe(this, Observer {
            if (!it.movie?.results.isNullOrEmpty() && !it.genre.isNullOrEmpty()) {
                Loading(true)
                recycler_movie.adapter =
                    MovieAdapter(
                        it.movie!!,
                        it.genre,
                        it.language
                    )
            } else {
                Loading(false)
            }
        })


    }


    private fun Loading(isDone: Boolean) {
        if (isDone) {
            recycler_movie.visibility = View.VISIBLE
            progresbar_movie.visibility = View.GONE
        } else {
            recycler_movie.visibility = View.GONE
            progresbar_movie.visibility = View.VISIBLE
        }
    }
}
