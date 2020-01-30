package com.rizkirakasiwi.made.fragment.ui.favorite.favoriteMovies

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.FavoriteAdapter
import com.rizkirakasiwi.made.fragment.database.DatabaseHelper
import kotlinx.android.synthetic.main.favorite_movies_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteMovies : Fragment() {

    companion object {
        fun newInstance() =
            FavoriteMovies()
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
        GlobalScope.launch(Dispatchers.Main) {
            val data = DatabaseHelper(this@FavoriteMovies.context!!).loadFav(DatabaseHelper.TABLE_MOVIE)
            Log.i("Adapter", data.toString())
            viewModel.setFavorite(data)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.favoriteData.observe(this, Observer {
            if (it.isNullOrEmpty()){
                Loading(false)
            }else{
                recycler_favorite_movie.adapter = FavoriteAdapter(it.toMutableList())
                Loading(true)
            }

        })
    }

    private fun Loading(isDone: Boolean) {
        if (isDone) {
            recycler_favorite_movie.visibility = View.VISIBLE
            progresbar_favorite_movie.visibility = View.GONE
        } else {
            recycler_favorite_movie.visibility = View.GONE
            progresbar_favorite_movie.visibility = View.VISIBLE
        }
    }

}
