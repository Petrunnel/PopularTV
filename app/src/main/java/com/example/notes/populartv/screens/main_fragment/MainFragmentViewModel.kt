package com.example.notes.populartv.screens.main_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.api.TvPost
import com.example.notes.populartv.repository.PopularTvPagingSource
import kotlinx.coroutines.flow.Flow

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var retrofitService: PopularTvApi

    init {
        retrofitService = PopularTvApi.create()
    }

    fun getListData() : Flow<PagingData<TvPost>> {
        return Pager (config = PagingConfig(pageSize = 20, maxSize = 500),
        pagingSourceFactory = {PopularTvPagingSource(retrofitService)}).flow.cachedIn(viewModelScope)
    }
}