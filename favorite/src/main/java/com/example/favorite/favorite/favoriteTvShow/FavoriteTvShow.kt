package com.rizkirakasiwi.made.fragment.ui.favorite.favoriteTvShow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.favorite.provider.DbContract
import com.example.favorite.adapter.FavoriteAdapter
import com.example.favorite.provider.MappingHelper
import com.example.favorite.R
import kotlinx.android.synthetic.main.favorite_tv_show_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteTvShow : Fragment() {

    companion object {
        fun newInstance() =
            FavoriteTvShow()
    }

    private lateinit var viewModel: FavoriteTvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_tv_show_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            val favorite = activity?.contentResolver?.query(DbContract.FavoriteColumn.CONTENT_URI_TVSHOW, null, null, null, null)
            val data = MappingHelper.mapCursorToArrayList(favorite)
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
                recycler_favorite_tv.adapter =
                    FavoriteAdapter(
                        it.toMutableList(),
                        false
                    )
                Loading(true)
            }

        })
    }

    private fun Loading(isDone: Boolean) {
        if (isDone) {
            recycler_favorite_tv.visibility = View.VISIBLE
            progresbar_favorite_tv.visibility = View.GONE
        } else {
            recycler_favorite_tv.visibility = View.GONE
            progresbar_favorite_tv.visibility = View.VISIBLE
        }
    }

}
