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

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.TvShowAdapter
import com.rizkirakasiwi.made.fragment.data.other.DataForAdapter
import com.rizkirakasiwi.made.fragment.data.movie.DataMovie
import com.rizkirakasiwi.made.fragment.model.TvViewModel
import kotlinx.android.synthetic.main.tv_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Tv : Fragment() {

    companion object {
        fun newInstance() = Tv()
    }

    private lateinit var viewModel: TvViewModel
    private lateinit var tv: DataMovie


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TvViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            val language = resources.getString(R.string.language)
            val tvShow = viewModel.getTvData(language)
            val genre = API.getGenre(API.genreTvUrl(language))
            val languageList = API.getLanguage(API.LanguageUrl())
            val dataForAdapter =
                DataForAdapter(tvshow = tvShow, genre = genre, language = languageList)
            viewModel.setData(dataForAdapter)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.tv_show)
        viewModel.dataForAdapter.observe(this, Observer {
            if (!it.tvshow?.results.isNullOrEmpty() && !it.genre.isNullOrEmpty()) {
                Loading(true)
                recycler_tv.adapter =
                    TvShowAdapter(
                        it.tvshow!!,
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
            recycler_tv.visibility = View.VISIBLE
            progresbar_tv.visibility = View.GONE
        } else {
            recycler_tv.visibility = View.GONE
            progresbar_tv.visibility = View.VISIBLE
        }
    }
}
