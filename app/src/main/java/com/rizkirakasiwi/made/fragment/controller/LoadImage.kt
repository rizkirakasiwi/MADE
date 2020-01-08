package com.rizkirakasiwi.made.fragment.controller

import android.widget.ImageView
import com.rizkirakasiwi.made.API
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object LoadImage {
    fun load(image_path:String, imageView: ImageView) = GlobalScope.launch(Dispatchers.Main) {
        val url = API.poster(image_path)
        Picasso.get().load(url).into(imageView)
    }
}