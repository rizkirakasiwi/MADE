package com.example.favorite.provider

import android.database.Cursor
import com.example.favorite.data.FavoriteDb

object MappingHelper {


    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<FavoriteDb> {
        val favList = ArrayList<FavoriteDb>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(FavoriteDb.ID))
                val title = getString(getColumnIndexOrThrow(FavoriteDb.JUDUL))
                val description = getString(getColumnIndexOrThrow(FavoriteDb.DESKRIPSI))
                val date = getString(getColumnIndexOrThrow(FavoriteDb.TAHUN))
                val genre = getString(getColumnIndexOrThrow(FavoriteDb.GENRE))
                val rating = getString(getColumnIndexOrThrow(FavoriteDb.RATING))
                val bahasa = getString(getColumnIndexOrThrow(FavoriteDb.BAHASA))
                val image = getString(getColumnIndexOrThrow(FavoriteDb.IMAGE_PATH))
                favList.add(
                    FavoriteDb(
                        id = id,
                        deskripsi = description,
                        rating = rating,
                        genre = genre,
                        image_path = image,
                        bahasa = bahasa,
                        tahun = date,
                        judul = title
                    )
                )
            }
        }
        return favList
    }
}