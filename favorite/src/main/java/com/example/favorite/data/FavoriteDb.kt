package com.example.favorite.data

import android.os.Parcel
import android.os.Parcelable

data class FavoriteDb(
    val id:String?,
    val judul:String?,
    val genre:String?,
    val rating:String?,
    val tahun:String?,
    val bahasa:String?,
    val deskripsi:String?,
    val image_path:String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(judul)
        parcel.writeString(genre)
        parcel.writeString(rating)
        parcel.writeString(tahun)
        parcel.writeString(bahasa)
        parcel.writeString(deskripsi)
        parcel.writeString(image_path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavoriteDb> {
        override fun createFromParcel(parcel: Parcel): FavoriteDb {
            return FavoriteDb(parcel)
        }

        override fun newArray(size: Int): Array<FavoriteDb?> {
            return arrayOfNulls(size)
        }

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