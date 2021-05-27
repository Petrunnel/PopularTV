package com.example.notes.populartv.ui.main_fragment

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.db.TvPostDatabase
import com.example.notes.populartv.di.TvPostModule
import com.example.notes.populartv.models.TvPost
import com.example.notes.populartv.repository.PopularTvRepository
import com.example.notes.populartv.utilits.APP_ACTIVITY
import kotlinx.coroutines.flow.Flow
import moxy.MvpPresenter

class MainPresenter : MvpPresenter<MainView>(){

    private val repository = TvPostModule.providePopularTvRepository()

    @ExperimentalPagingApi
    fun getTvPosts(): Flow<PagingData<TvPost>> {
        return repository.getTvPostsFromRemoteMediator()
    }
}