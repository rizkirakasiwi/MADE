package com.rizkirakasiwi.made.fragment.data.tvShow

data class DataTv(
    val page: Int,
    val results: List<TvResult>,
    val total_pages: Int,
    val total_results: Int
)