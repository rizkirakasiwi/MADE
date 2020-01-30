package com.rizkirakasiwi.made.fragment.ui.home.tvShow

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.rizkirakasiwi.made.fragment.controller.API

import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.TvShowAdapter
import com.rizkirakasiwi.made.fragment.data.other.DataForAdapter
import kotlinx.android.synthetic.main.tv_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Tv : Fragment() {

    companion object {
        fun newInstance() = Tv()
    }

    private val TAG = "TVShow"
    private lateinit var viewModel: TvViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TvViewModel::class.java)
        if (isOnline()) {
            GlobalScope.launch(Dispatchers.Main) {
                val language = resources.getString(R.string.language)
                val tvShow = viewModel.getTvData(language)
                val genre = API.getGenre(API.genreTvUrl(language))
                val languageList = API.getLanguage(API.LanguageUrl())
                val dataForAdapter =
                    DataForAdapter(tvshow = tvShow, genre = genre, language = languageList)
                viewModel.setData(dataForAdapter)
            }
        }else{
            Log.i(TAG, "Network is available")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dataForAdapter.observe(this, Observer {
            Loading(true)
            recycler_tv.adapter =
                TvShowAdapter(
                    it.tvshow!!,
                    it.genre!!,
                    it.language!!
                )
        })
    }


    fun isOnline(): Boolean {
        val connMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
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
