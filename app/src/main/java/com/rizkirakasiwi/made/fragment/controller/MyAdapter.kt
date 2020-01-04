package com.rizkirakasiwi.made.fragment.controller

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.data.MovieData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.catalog_ui.view.*

class MyAdapter (val data:List<MovieData>, val image:List<Int>):RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.catalog_ui, parent, false)
    )

    override fun getItemCount() = data.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val view = holder.itemView
        view.txt_durasi.text = data[position].durasi
        view.txt_genre.text = data[position].genre
        view.txt_judul.text = data[position].judulMovie
        view.txt_rating.text = data[position].rating
        Picasso.get().load(image[position]).into(view.img_banner)

        Log.i("Adapter", "data $data \nmovie$image")
    }

}