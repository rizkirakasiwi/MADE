package com.rizkirakasiwi.made.fragment.ui.home.movie

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.rizkirakasiwi.made.fragment.controller.API
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
        if(isOnline()) {
            search("")
        }else{
            Log.i(TAG, "Network is not available")
        }
    }


    fun isOnline(): Boolean {
        val connMgr = activity?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }





    override fun onResume() {
        super.onResume()

        src_searcview_movie.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               GlobalScope.launch(Dispatchers.Main){
                   if(isOnline()) {
                       search(newText)
                   }else{
                       Log.i(TAG, "Network is not available")
                   }
               }

                return true
            }
        })

        viewModel.dataForAdapter.observe(this, Observer {
            if(it.movie?.results.isNullOrEmpty()) Loading(false)
            else {
                Loading(true)
                recycler_movie.adapter =
                    MovieAdapter(
                        it.movie!!,
                        it.genre!!,
                        it.language!!
                    )
            }
        })
    }


    private fun search(newText:String?){
        GlobalScope.launch(Dispatchers.Main) {
            val language = resources.getString(R.string.language)
            val genre = API.getGenre(API.genreMovieUrl(language))
            val languageList = API.getLanguage(API.LanguageUrl())

            if(newText?.length == 0){
                val movie = viewModel.getMovieData(language)
                val dataForAdapter =
                    DataForAdapter(movie = movie, genre = genre, language = languageList)
                viewModel.setData(dataForAdapter)
            }else{
                val movie = viewModel.getMovieDataFromSearch(language, newText)
                val dataForAdapter =
                    DataForAdapter(movie = movie, genre = genre, language = languageList)
                viewModel.setData(dataForAdapter)
            }
        }
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
