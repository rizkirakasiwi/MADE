package com.rizkirakasiwi.made.fragment.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.API
import com.rizkirakasiwi.made.fragment.data.movie.MovieResult
import com.rizkirakasiwi.made.fragment.data.other.DataDetail
import com.rizkirakasiwi.made.fragment.data.tvShow.TvResult
import com.rizkirakasiwi.made.fragment.model.DetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Detail : Fragment() {

    companion object {
        fun newInstance() = Detail()
        const val MOVIE = "Movie"
        const val TVSHOW = "TvShow"
        const val GENRE = "generate"
        const val LANGUAGE = "language"
    }

    private lateinit var viewModel: DetailViewModel

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
        val tvData = arguments?.getParcelable<TvResult>(TVSHOW)
        val movieData = arguments?.getParcelable<MovieResult>(MOVIE)
        val genre = arguments?.getStringArrayList(GENRE)
        val language = arguments?.getString(LANGUAGE)
        viewModel.setMovieData(
            DataDetail(
                tv = tvData,
                movie = movieData,
                genre = genre,
                language = language
            )
        )
    }

    override fun onResume() {
        super.onResume()
       (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.detail)
        viewModel.dataDetail.observe(this, Observer {
            txt_detail_deskripsi.text = it.movie?.overview ?: it.tv?.overview
            txt_detail_judultahun.text = resources.getString(
                R.string.judul,
                it.tv?.original_name ?: it.movie?.original_title,
                it.tv?.first_air_date?.substring(0, 4) ?: it.movie?.release_date?.substring(0, 4)
            )

            val vote_average = it.tv?.vote_average ?: it.movie?.vote_average

            txt_detail_rating.text = resources.getString(
                R.string.rating,
                vote_average.toString()
            )
            txt_detail_genre.text =
                resources.getString(R.string.genre, it.genre?.joinToString(", "))
            load(it.tv?.poster_path ?: it.movie?.poster_path, img_detail_banner)
            txt_detail_language.text = resources.getString(R.string.original_language, it.language)
        })
    }

    private fun load(image_path: String?, imageView: ImageView) =
        GlobalScope.launch(Dispatchers.Main) {
            if (image_path != null) {
                val url = API.poster(image_path)
                Picasso.get().load(url).into(imageView)
            }
        }


}
