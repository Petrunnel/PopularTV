package com.example.notes.populartv.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.models.TvPost
import com.example.notes.populartv.db.TvPostDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PopularTvRepository (
    private val api: PopularTvApi,
    private val db: TvPostDatabase
) {

    @ExperimentalPagingApi
    fun getTvPostsFromRemoteMediator(): Flow<PagingData<TvPost>> {
        val pagingSourceFactory = { db.getTvPostDao().getAll() }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = PopularTvRemoteMediator(
                api,
                db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}