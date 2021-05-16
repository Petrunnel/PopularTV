package com.example.notes.populartv.screens.main_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.api.TvPost
import com.example.notes.populartv.db.TvPostDatabase
import com.example.notes.populartv.repository.PopularTvRepository
import kotlinx.coroutines.flow.Flow


private const val PAGE_SIZE = 20

class MainFragmentViewModel (
    private val repository: PopularTvRepository,
) : ViewModel() {

    @ExperimentalPagingApi
    fun getTvPosts(): Flow<PagingData<TvPost>> {
        return repository.getTvPostsFromRemoteMediator().cachedIn(viewModelScope)
    }
}