package com.rizkirakasiwi.made.fragment.ui

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.model.DetailViewModel
import com.rizkirakasiwi.made.fragment.data.MovieData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_fragment.*

class Detail : Fragment() {

    companion object {
        fun newInstance() = Detail()
        val MOVIE = "Movie"
        val IMAGE = "Image"
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var data : MovieData
    private var image = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        val movieData = arguments?.getParcelable<MovieData>(MOVIE)
        val imageData = arguments?.getInt(IMAGE)
        viewModel.setData(movieData, imageData)
        viewModel.movieData.observe(this, Observer {
            data = it
        })
        viewModel.imageData.observe(this, Observer {
            image = it
        })
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.GONE
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.detail)

        txt_detail_deskripsi.text = data.keterangan
        txt_detail_durasi.text = resources.getString(R.string.durasi, data.durasi)
        txt_detail_genre.text = resources.getString(R.string.genre, data.genre)
        txt_detail_judultahun.text = resources.getString(R.string.judul, data.judulMovie, data.tahun)
        txt_detail_rating.text = resources.getString(R.string.rating, data.rating)
        Picasso.get().load(image).into(img_detail_banner)
    }

    override fun onStop() {
        super.onStop()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.VISIBLE
    }

}
