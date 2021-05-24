package com.example.notes.populartv.ui.main_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.notes.populartv.models.TvPost
import com.example.notes.populartv.repository.PopularTvRepository
import kotlinx.coroutines.flow.Flow


private const val PAGE_SIZE = 20

class MainFragmentViewModel (
    private val repository: PopularTvRepository
) : ViewModel() {

    @ExperimentalPagingApi
    fun getTvPosts(): Flow<PagingData<TvPost>> {
        return repository.getTvPostsFromRemoteMediator().cachedIn(viewModelScope)
    }
}