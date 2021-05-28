package com.example.notes.populartv.api

import com.example.notes.populartv.models.SearchTvPost

class SearchTvList (
    val results: List<SearchTvPost>,
    val page: Int,
    val total_pages: Int
)