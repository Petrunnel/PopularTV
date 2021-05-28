package com.example.notes.populartv.ui.main_fragment

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.db.TvPostDatabase
import com.example.notes.populartv.db.search.SearchTvPostDatabase
import com.example.notes.populartv.models.SearchTvPost
import com.example.notes.populartv.models.TvPost
import com.example.notes.populartv.repository.PopularTvRepository
import com.example.notes.populartv.repository.SearchTvRepository
import com.example.notes.populartv.utilits.APP_ACTIVITY
import kotlinx.coroutines.flow.Flow
import moxy.MvpPresenter

class MainPresenter : MvpPresenter<MainView>() {

    @ExperimentalPagingApi
    fun getTvPosts(): Flow<PagingData<TvPost>> {
        val repository = PopularTvRepository(
            PopularTvApi.create(),
            TvPostDatabase.getInstance(APP_ACTIVITY)
        )
        return repository.getTvPostsFromRemoteMediator()
    }

    @ExperimentalPagingApi
    fun getTvPosts(searchRequest: String): Flow<PagingData<SearchTvPost>> {
        val repository = SearchTvRepository(
            PopularTvApi.create(),
            SearchTvPostDatabase.getInstance(APP_ACTIVITY),
            searchRequest
        )
        return repository.getTvPostsFromRemoteMediator()
    }
}