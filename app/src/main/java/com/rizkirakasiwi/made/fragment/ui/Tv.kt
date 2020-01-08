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

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.MovieAdapter
import com.rizkirakasiwi.made.fragment.controller.ShowLoading
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
    private lateinit var tv : DataMovie


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TvViewModel::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            val tvShow = viewModel.getTvData()
            val genre = API.getGenre(API.genreTvUrl())
            val dataForAdapter =
                DataForAdapter(tvshow = tvShow, genre = genre)
            viewModel.setData(dataForAdapter)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ShowLoading.initilizeView(recycler_tv, progresbar_tv)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.tv_show)
        viewModel.dataForAdapter.observe(this, Observer {
            if(!it.tvshow?.results.isNullOrEmpty() && !it.genre.isNullOrEmpty()) {
                ShowLoading.isDone()
                recycler_tv.adapter = TvShowAdapter(it.tvshow!!, it.genre)
            }else{
                ShowLoading.isLoad()
            }
        })
    }
}
