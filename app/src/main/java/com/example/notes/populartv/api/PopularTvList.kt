package com.example.notes.populartv.api

import com.example.notes.populartv.models.TvPost

data class PopularTvList(
    val results: List<TvPost>,
    val page: Int,
    val total_pages: Int
)
