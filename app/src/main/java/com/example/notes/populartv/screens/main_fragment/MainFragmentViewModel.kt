package com.example.notes.populartv.screens.main_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.api.TvPost
import com.example.notes.populartv.db.TvPostDatabase
import com.example.notes.populartv.repository.PopularTvRemoteMediator
import kotlinx.coroutines.flow.Flow


private const val PAGE_SIZE = 20

class MainFragmentViewModel (application: Application, ) : AndroidViewModel(application) {

    private val database: TvPostDatabase
    lateinit var retrofitService: PopularTvApi

    init {
        retrofitService = PopularTvApi.create()
        database = TvPostDatabase.create(application)
    }

    @ExperimentalPagingApi
    fun getTvPostsFromMediator(): Flow<PagingData<TvPost>> {
        val pagingSourceFactory = { database.getTvPostDao().getAll() }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            remoteMediator = PopularTvRemoteMediator(
                retrofitService,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.cachedIn(viewModelScope)
    }
}