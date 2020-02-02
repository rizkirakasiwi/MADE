package com.rizkirakasiwi.made.fragment.ui.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.API
import com.rizkirakasiwi.made.fragment.data.other.FavoriteDb
import com.rizkirakasiwi.made.fragment.data.movie.MovieResult
import com.rizkirakasiwi.made.fragment.data.other.DataDetail
import com.rizkirakasiwi.made.fragment.data.tvShow.TvResult
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
        const val FAVORITE = "favorite_movie"
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
        val tvData =
            arguments?.getParcelable<TvResult>(TVSHOW)
        val favorite = arguments?.getParcelable<FavoriteDb>(
            FAVORITE
        )
        val movieData = arguments?.getParcelable<MovieResult>(MOVIE)
        val genre = arguments?.getStringArrayList(GENRE)
        val language = arguments?.getString(LANGUAGE)
        viewModel.setMovieData(
            DataDetail(
                tv = tvData,
                movie = movieData,
                genre = genre,
                language = language,
                favorite = favorite
            )
        )
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.detail)

        viewModel.dataDetail.observe(this, Observer {
            loadData(it)
        })
    }


    private fun loadData(it : DataDetail){
        txt_detail_deskripsi.text =
            it.movie?.overview ?: it.tv?.overview ?: it.favorite?.deskripsi
        txt_detail_judultahun.text = resources.getString(
            R.string.judul,
            it.tv?.original_name ?: it.movie?.original_title ?: it.favorite?.judul,
            it.tv?.first_air_date?.substring(0, 4) ?: it.movie?.release_date?.substring(0, 4)
            ?: it.favorite?.tahun?.substring(0, 4)
        )

        val vote_average = it.tv?.vote_average?.toFloat() ?: it.movie?.vote_average?.toFloat()
        ?: it.favorite?.rating?.toFloat()
        img_detail_rating.rating = vote_average!! / 2f

        txt_detail_rating.text = vote_average.toString()
        txt_detail_genre.text = it.genre?.joinToString(", ") ?: it.favorite?.genre
        load(
            it.tv?.poster_path ?: it.movie?.poster_path ?: it.favorite?.image_path,
            img_detail_banner
        )
        txt_detail_language.text = it.favorite?.bahasa ?: it.language
    }


    private fun load(image_path: String?, imageView: ImageView) =
        GlobalScope.launch(Dispatchers.Main) {
            if (image_path != null) {
                val url = API.poster(image_path)
                Picasso.get().load(url).into(imageView)
            }
        }


}
