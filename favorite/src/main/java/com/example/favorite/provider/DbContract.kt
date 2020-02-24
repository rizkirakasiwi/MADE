package com.example.favorite.provider

import android.net.Uri
import android.provider.BaseColumns

object DbContract {
    const val AUTHORITY = "com.rizkirakasiwi.made"
    const val SCHEME = "content"

    class FavoriteColumn : BaseColumns {

        companion object {
            const val MOVIE = "FavotiteMovie"
            const val TVSHOW = "FavoriteTvShow"

            val CONTENT_URI_MOVIE: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(MOVIE)
                .build()

            val CONTENT_URI_TVSHOW: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TVSHOW)
                .build()


        }

    }
}