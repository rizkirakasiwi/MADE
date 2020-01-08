package com.rizkirakasiwi.made.fragment.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.data.movie.DataMovie
import kotlinx.android.synthetic.main.catalog_ui.view.*

class MovieAdapter (val data: DataMovie, val genre:HashMap<Int,String>, val isMovie:Boolean = true):RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.catalog_ui, parent, false)
    )

    override fun getItemCount() = data.results.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val view = holder.itemView
        view.txt_rating.text = data.results[position].vote_average.toString()
        view.txt_judul.text = data.results[position].original_title
        LoadImage.load(data.results[position].poster_path, view.img_banner)

        val my_genre = mutableListOf<String?>()
        data.results[position].genre_ids.forEach {
            my_genre.add(genre[it])
        }
        view.txt_genre.text = my_genre.joinToString(", ")
    }
}
