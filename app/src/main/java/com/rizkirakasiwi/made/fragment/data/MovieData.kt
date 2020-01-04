package com.rizkirakasiwi.made.fragment.data

import android.os.Parcel
import android.os.Parcelable


data class MovieData (
    val idMovie : String?,
    val judulMovie:String?,
    val tahun:String?,
    val durasi:String?,
    val rating:String?,
    val keterangan:String?,
    val genre:String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idMovie)
        parcel.writeString(judulMovie)
        parcel.writeString(tahun)
        parcel.writeString(durasi)
        parcel.writeString(rating)
        parcel.writeString(keterangan)
        parcel.writeString(genre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieData> {
        override fun createFromParcel(parcel: Parcel): MovieData {
            return MovieData(parcel)
        }

        override fun newArray(size: Int): Array<MovieData?> {
            return arrayOfNulls(size)
        }
    }
}
