package com.rizkirakasiwi.made.fragment.controller

import android.content.ContentValues
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.database.DatabaseHelper
import com.rizkirakasiwi.made.fragment.database.DatabaseHelper.Companion.TABLE_TVSHOW
import com.rizkirakasiwi.made.fragment.controller.GenerateToGenreName.generate
import com.rizkirakasiwi.made.fragment.data.FavoriteDb
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
        val myViewHolder = MyViewHolder(view)
        view.setOnClickListener {
            val my_genre = generate(data.results[myViewHolder.adapterPosition].genre_ids, genre)
            val language =
                this.language[data.results[myViewHolder.adapterPosition].original_language]
            val bundle = bundleOf(
                Detail.TVSHOW to data.results[myViewHolder.adapterPosition],
                Detail.GENRE to my_genre,
                Detail.LANGUAGE to language
            )
            it.findNavController().navigate(R.id.action_homeMain_to_detail, bundle)
        }

        view.img_favorite.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                if (view.img_favorite.isChecked) {
                    DatabaseHelper(view.context)
                        .insertData(
                        TABLE_TVSHOW,
                        values(myViewHolder.adapterPosition)
                    )

                } else {
                    DatabaseHelper(view.context)
                        .deleteFav(
                        TABLE_TVSHOW,
                        data.results[myViewHolder.adapterPosition].id.toString()
                    )
                }
            }
        }
        return myViewHolder
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

        val rating = data.results[position].vote_average.toFloat()
        view.img_rating.rating = rating / 2f

        load(
            data.results[position].poster_path,
            view.img_banner
        )

        val my_genre = generate(data.results[position].genre_ids, genre)
        view.txt_genre.text = my_genre.joinToString(", ")

        GlobalScope.launch(Dispatchers.Main) {
            val favoriteData = DatabaseHelper(
                view.context
            ).loadFav(TABLE_TVSHOW)
            val favorite = favoriteData.find { data.results[position].id.toString() == it.id }
            view.img_favorite.isChecked = (favorite != null)
        }
    }

    private fun load(image_path: String?, imageView: ImageView) =
        GlobalScope.launch(Dispatchers.Main) {
            if (image_path != null) {
                val url = API.poster(image_path)
                Picasso.get().load(url).into(imageView)
            }
        }

    private fun values(position: Int): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(FavoriteDb.ID, data.results[position].id)
        contentValues.put(
            FavoriteDb.TAHUN,
            data.results[position].first_air_date
        )
        contentValues.put(FavoriteDb.JUDUL, data.results[position].name)
        contentValues.put(
            FavoriteDb.RATING,
            data.results[position].vote_average
        )
        contentValues.put(
            FavoriteDb.IMAGE_PATH,
            data.results[position].poster_path
        )
        contentValues.put(
            FavoriteDb.GENRE,
            generate(
                data.results[position].genre_ids,
                genre
            ).joinToString(", ")
        )
        contentValues.put(
            FavoriteDb.DESKRIPSI,
            data.results[position].overview
        )
        contentValues.put(
            FavoriteDb.BAHASA,
            data.results[position].original_language
        )


        return contentValues
    }
}
