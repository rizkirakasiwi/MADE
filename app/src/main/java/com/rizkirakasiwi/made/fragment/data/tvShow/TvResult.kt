package com.rizkirakasiwi.made.fragment.data.tvShow

import android.os.Parcel
import android.os.Parcelable

data class TvResult(
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String?,
    val origin_country: List<String>?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val vote_average: Double,
    val vote_count: Int
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        TODO("genre_ids"),
        parcel.readInt(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(backdrop_path)
        parcel.writeString(first_air_date)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeStringList(origin_country)
        parcel.writeString(original_language)
        parcel.writeString(original_name)
        parcel.writeString(overview)
        parcel.writeDouble(popularity)
        parcel.writeString(poster_path)
        parcel.writeDouble(vote_average)
        parcel.writeInt(vote_count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TvResult> {
        override fun createFromParcel(parcel: Parcel): TvResult {
            return TvResult(parcel)
        }

        override fun newArray(size: Int): Array<TvResult?> {
            return arrayOfNulls(size)
        }
    }
}