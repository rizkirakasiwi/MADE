package com.rizkirakasiwi.made.fragment.controller

import android.annotation.SuppressLint
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("StaticFieldLeak")
object ShowLoading {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    fun initilizeView(recyclerView: RecyclerView, progressBar: ProgressBar){
        this.recyclerView = recyclerView
        this.progressBar = progressBar
    }
    fun isDone(){
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    fun isLoad(){
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }
}