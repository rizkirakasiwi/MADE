package com.rizkirakasiwi.made.fragment.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.MyAdapter
import com.rizkirakasiwi.made.fragment.controller.ShowLoading
import com.rizkirakasiwi.made.fragment.data.MovieData
import com.rizkirakasiwi.made.fragment.model.TvViewModel
import kotlinx.android.synthetic.main.movie_fragment.*
import kotlinx.android.synthetic.main.tv_fragment.*

class Tv : Fragment() {

    companion object {
        fun newInstance() = Tv()
    }

    private lateinit var viewModel: TvViewModel
    private lateinit var image:MutableList<Int>
    private lateinit var movie:MutableList<MovieData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TvViewModel::class.java)
        image = mutableListOf()
        movie = mutableListOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tvData(view)
        viewModel.imageData(view)

        ShowLoading.initilizeView(recycler_tv, progresbar_tv)

        viewModel.listImage.observe(this, Observer {
            image = it.toMutableList()
        })

        viewModel.listTv.observe(this, Observer {
            movie = it.toMutableList()
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.tv_show)

        if(movie.isNullOrEmpty() && image.isNullOrEmpty()){
            ShowLoading.isLoad()
        }else {
            recycler_tv.adapter = MyAdapter(movie, image)
            ShowLoading.isDone()
        }
    }
}
