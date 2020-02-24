package com.example.favorite.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.favorite.R
import com.example.favorite.api.API
import com.example.favorite.data.FavoriteDb
import com.example.favorite.provider.DbContract.FavoriteColumn.Companion.CONTENT_URI_MOVIE
import com.example.favorite.provider.DbContract.FavoriteColumn.Companion.CONTENT_URI_TVSHOW
import com.example.favorite.provider.MappingHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.catalog_ui.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteAdapter (val data: MutableList<FavoriteDb>, val isMovie : Boolean = true) : RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.catalog_ui, parent, false)
        val myViewHolder = MyViewHolder(view)

        view.img_favorite.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                if (isMovie) {
                    if (view.img_favorite.isChecked) {
                        view.context.contentResolver.insert(CONTENT_URI_MOVIE,values(
                            myViewHolder.adapterPosition
                        ))
                    } else {
                        AlertDialog.Builder(view.context)
                            .setTitle(view.context.getString(R.string.remove))
                            .setMessage(view.context.getString(R.string.are_you_sure))
                            .setCancelable(false)
                            .setPositiveButton(view.context.getString(R.string.yes)){ _, _ ->
                                view.context.contentResolver.delete(CONTENT_URI_MOVIE,
                                    FavoriteDb.ID, arrayOf(data[myViewHolder.adapterPosition].id))
                                data.removeAt(myViewHolder.adapterPosition)
                                notifyItemRemoved(myViewHolder.adapterPosition)
                                notifyDataSetChanged()
                            }
                            .setNegativeButton(view.context.getString(R.string.cancel)){ _, _ ->
                                view.img_favorite.isChecked = true
                            }
                            .create()
                            .show()
                    }
                }else{
                    if (view.img_favorite.isChecked) {
                        view.context.contentResolver.insert(
                            CONTENT_URI_TVSHOW,values(
                            myViewHolder.adapterPosition
                        ))

                    } else {
                        AlertDialog.Builder(view.context)
                            .setTitle(view.context.getString(R.string.remove))
                            .setCancelable(false)
                            .setMessage(view.context.getString(R.string.are_you_sure))
                            .setPositiveButton(view.context.getString(R.string.yes)){ _, _ ->
                                view.context.contentResolver.delete(CONTENT_URI_TVSHOW,
                                    FavoriteDb.ID, arrayOf(data[myViewHolder.adapterPosition].id))
                                data.removeAt(myViewHolder.adapterPosition)
                                notifyItemRemoved(myViewHolder.adapterPosition)
                                notifyDataSetChanged()
                            }
                            .setNegativeButton(view.context.getString(R.string.cancel)){ _, _ ->
                                view.img_favorite.isChecked = true
                            }
                            .create()
                            .show()

                    }
                }
            }
        }

        return myViewHolder
    }

    private fun values(position: Int): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(FavoriteDb.ID, data[position].id)
        contentValues.put(
            FavoriteDb.TAHUN,
            data[position].tahun
        )
        contentValues.put(FavoriteDb.JUDUL, data[position].judul)
        contentValues.put(
            FavoriteDb.RATING,
            data[position].rating
        )
        contentValues.put(
            FavoriteDb.IMAGE_PATH,
            data[position].image_path
        )
        contentValues.put(
            FavoriteDb.GENRE,
            data[position].genre
        )
        contentValues.put(
            FavoriteDb.DESKRIPSI,
            data[position].deskripsi
        )
        contentValues.put(
            FavoriteDb.BAHASA,
            data[position].bahasa
        )


        return contentValues
    }
    override fun getItemCount() = data.count()

    @SuppressLint("Recycle")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val view = holder.itemView
        view.txt_rating.text = data[position].rating
        view.txt_judul.text = view.resources.getString(
            R.string.judul,
            data[position].judul,
            data[position].tahun?.substring(0, 4)
        )
        view.txt_genre.text = data[position].genre
        view.txt_language.text = data[position].bahasa
        val rating = data[position].rating?.toFloat()
        view.img_rating.rating = rating!! / 2f
        load(
            data[position].image_path,
            view.img_banner
        )

        GlobalScope.launch(Dispatchers.Main) {
            val movieCursor = view.context.contentResolver.query(CONTENT_URI_MOVIE,null,null,null,null)
            val tvshowCursor = view.context.contentResolver.query(CONTENT_URI_TVSHOW,null,null,null,null)
            val favoriteData = if(isMovie) MappingHelper.mapCursorToArrayList(movieCursor)
            else MappingHelper.mapCursorToArrayList(tvshowCursor)

            val favorite = favoriteData.find { data[position].id == it.id }
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

}