package com.rizkirakasiwi.made.fragment.data

data class FavoriteDb(
    val id:String,
    val judul:String,
    val genre:String,
    val rating:String,
    val tahun:String,
    val bahasa:String,
    val deskripsi:String,
    val image_path:String
){
    companion object{
        val ID = "id"
        val JUDUL = "judul"
        val GENRE = "genre"
        val RATING = "rating"
        val TAHUN = "tahun"
        val BAHASA = "bahasa"
        val DESKRIPSI = "deskripsi"
        val IMAGE_PATH = "image_path"
    }
}