package com.rizkirakasiwi.made.contentProvider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.rizkirakasiwi.made.contentProvider.DbContract.AUTHORITY
import com.rizkirakasiwi.made.contentProvider.DbContract.FavoriteColumn.Companion.CONTENT_URI_MOVIE
import com.rizkirakasiwi.made.contentProvider.DbContract.FavoriteColumn.Companion.CONTENT_URI_TVSHOW
import com.rizkirakasiwi.made.database.DatabaseHelper.Companion.TABLE_MOVIE
import com.rizkirakasiwi.made.database.DatabaseHelper.Companion.TABLE_TVSHOW

class MyContentProvider : ContentProvider() {



    companion object{
        private const val MOVIE = 1
        private const val TVSHOW = 2
        private const val MOVIE_ID = 3
        private const val TVSHOW_ID = 4
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var movieHelper : MovieHelper
        private lateinit var tvShowHelper: TvShowHelper


        init {

            sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE,
                MOVIE
            )
            sUriMatcher.addURI(AUTHORITY,
                "$TABLE_MOVIE/#",
                MOVIE_ID
            )

            sUriMatcher.addURI(AUTHORITY, TABLE_TVSHOW,
                TVSHOW
            )
            sUriMatcher.addURI(AUTHORITY,
                "$TABLE_TVSHOW/#",
                TVSHOW_ID
            )


        }
    }

    private fun deleteMovie(uri: Uri):Int{
        val delete = movieHelper.deleteById(uri.lastPathSegment.toString())
        context?.contentResolver?.notifyChange(CONTENT_URI_MOVIE, null)
        return delete
    }

    private fun deleteTvShow(uri: Uri):Int{
        val delete = tvShowHelper.deleteById(uri.lastPathSegment.toString())
        context?.contentResolver?.notifyChange(CONTENT_URI_TVSHOW, null)
        return delete
    }
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when(sUriMatcher.match(uri)){
            MOVIE_ID -> deleteMovie(uri)
            TVSHOW_ID -> deleteTvShow(uri)
            else -> 0
        }
    }

    override fun getType(uri: Uri): String? {
       return null
    }

    private fun insertMovie(values: ContentValues?):Uri?{
        val added = movieHelper.insert(values)
        context?.contentResolver?.notifyChange(CONTENT_URI_MOVIE, null)
        return Uri.parse("$CONTENT_URI_MOVIE/$added")
    }

    private fun insertTvShow(values: ContentValues?):Uri?{
        val added = tvShowHelper.insert(values)
        context?.contentResolver?.notifyChange(CONTENT_URI_TVSHOW, null)
        return Uri.parse("$CONTENT_URI_TVSHOW/$added")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when(sUriMatcher.match(uri)){
            MOVIE -> insertMovie(values)
            TVSHOW -> insertTvShow(values)
            else -> null
        }
    }

    override fun onCreate(): Boolean {
        movieHelper = MovieHelper.getInstance(context as Context)
        movieHelper.open()
        tvShowHelper =
            TvShowHelper.getInstance(context as Context)
        tvShowHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)){
            MOVIE -> movieHelper.queryAll()
            MOVIE_ID -> movieHelper.queryById(uri.lastPathSegment.toString())
            TVSHOW -> tvShowHelper.queryAll()
            TVSHOW_ID -> tvShowHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    private fun updateMovie(uri:Uri, values: ContentValues?):Int{
        val update = movieHelper.update(uri.lastPathSegment.toString(), values)
        context?.contentResolver?.notifyChange(CONTENT_URI_MOVIE, null)
        return update
    }

    private fun updateTvShow(uri:Uri, values: ContentValues?):Int{
        val update = movieHelper.update(uri.lastPathSegment.toString(), values)
        context?.contentResolver?.notifyChange(CONTENT_URI_TVSHOW, null)
        return update
    }
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return when(sUriMatcher.match(uri)){
            MOVIE_ID -> updateMovie(uri, values)
            TVSHOW_ID -> updateTvShow(uri, values)
            else -> 0
        }
    }
}
