package com.rizkirakasiwi.made.fragment.controller

object GenerateToGenreName {
    fun generate(genre_ids: List<Int>?, genre: HashMap<Int, String>): MutableList<String?> {
        val my_genre = mutableListOf<String?>()
        genre_ids?.forEach {
            my_genre.add(genre[it])
        }
        return my_genre
    }
}