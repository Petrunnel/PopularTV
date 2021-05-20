package com.example.notes.populartv.api

data class PopularTvList(
    val results: List<TvPost>,
    val page: Int,
    val total_pages: Int
)
