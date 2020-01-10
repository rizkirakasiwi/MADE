package com.rizkirakasiwi.made.fragment.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.GenerateToGenreName.generate
import com.rizkirakasiwi.made.fragment.data.tvShow.DataTv
import com.rizkirakasiwi.made.fragment.ui.Detail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.catalog_ui.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TvShowAdapter(
    private val data: DataTv,
    private val genre: HashMap<Int, String>,
    private val language: HashMap<String, String>
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.catalog_ui, parent, false)
        view.setOnClickListener {
            val my_genre = generate(data.results[viewType].genre_ids, genre)
            val language = this.language[data.results[viewType].original_language]
            val bundle = bundleOf(
                Detail.TVSHOW to data.results[viewType],
                Detail.GENRE to my_genre,
                Detail.LANGUAGE to language
            )
            it.findNavController().navigate(R.id.action_tv_to_detail, bundle)
        }
        return MyViewHolder(view)
    }

    override fun getItemCount() = data.results.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val view = holder.itemView
        view.txt_rating.text = data.results[position].vote_average.toString()
        view.txt_judul.text = view.resources.getString(
            R.string.judul,
            data.results[position].original_name,
            data.results[position].first_air_date?.substring(0, 4)
        )

        view.txt_language.text = language[data.results[position].original_language]

        load(
            data.results[position].poster_path,
            view.img_banner
        )

        val my_genre = generate(data.results[position].genre_ids, genre)
        view.txt_genre.text = my_genre.joinToString(", ")
    }

    private fun load(image_path: String?, imageView: ImageView) =
        GlobalScope.launch(Dispatchers.Main) {
            if (image_path != null) {
                val url = API.poster(image_path)
                Picasso.get().load(url).into(imageView)
            }
        }
}
