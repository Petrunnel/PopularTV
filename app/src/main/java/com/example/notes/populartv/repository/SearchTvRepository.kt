package com.example.notes.populartv.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.db.search.SearchTvPostDatabase
import com.example.notes.populartv.models.SearchTvPost
import kotlinx.coroutines.flow.Flow

class SearchTvRepository(
    private val api: PopularTvApi,
    private val db: SearchTvPostDatabase,
    private val searchRequest: String
) {

    @ExperimentalPagingApi
    fun getTvPostsFromRemoteMediator(): Flow<PagingData<SearchTvPost>> {
        val pagingSourceFactory = { db.getTvPostDao().getSearchResults("%$searchRequest%") }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = SearchTvRemoteMediator(
                api,
                db,
                searchRequest
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}