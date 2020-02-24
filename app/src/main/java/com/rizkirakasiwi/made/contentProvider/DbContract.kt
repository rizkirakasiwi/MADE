package com.rizkirakasiwi.made.contentProvider

import android.net.Uri
import android.provider.BaseColumns
import com.rizkirakasiwi.made.database.DatabaseHelper.Companion.TABLE_MOVIE
import com.rizkirakasiwi.made.database.DatabaseHelper.Companion.TABLE_TVSHOW

object DbContract {
    const val AUTHORITY = "com.rizkirakasiwi.made"
    const val SCHEME = "content"

    class FavoriteColumn : BaseColumns {

        companion object {
            const val MOVIE = TABLE_MOVIE
            const val TVSHOW = TABLE_TVSHOW

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