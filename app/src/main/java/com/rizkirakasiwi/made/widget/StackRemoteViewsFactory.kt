package com.rizkirakasiwi.made.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.rizkirakasiwi.made.R
import com.rizkirakasiwi.made.fragment.controller.API
import com.rizkirakasiwi.made.fragment.data.other.FavoriteDb
import com.rizkirakasiwi.made.fragment.database.DatabaseHelper
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception


internal class StackRemoteViewsFactory(private val context: Context, intent: Intent) :
    RemoteViewsService.RemoteViewsFactory {
    private val data = arrayListOf<FavoriteDb>()

    private val appWidgetId: Int = intent.getIntExtra(
        AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID
    )

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        val dataMovie = DatabaseHelper(context).loadFav(DatabaseHelper.TABLE_MOVIE)
        val dataTvShow = DatabaseHelper(context).loadFav(DatabaseHelper.TABLE_TVSHOW)
        data.addAll(dataMovie)
        data.addAll(dataTvShow)

    }

    override fun hasStableIds(): Boolean = false
    override fun getViewAt(position: Int): RemoteViews {

        val rv = RemoteViews(context.packageName, R.layout.widget_favorite_item)

        val url = API.poster(data[position].image_path!!)
        val bitmap = Picasso.get().load(url).get()
        rv.setImageViewBitmap(R.id.img_widget_banner, bitmap)

        rv.setTextViewText(R.id.txt_widget_judul, data[position].judul)
        rv.setTextViewText(R.id.txt_widget_genre, data[position].genre)
        rv.setTextViewText(R.id.txt_widget_language, data[position].bahasa)
        rv.setTextViewText(R.id.txt_widget_rating, data[position].rating)
        val extras = bundleOf(FavoriteWidget.EXTRA_ITEM to position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.linear_widget_item, fillInIntent)
        return rv

    }

    override fun getCount(): Int = data.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }
}