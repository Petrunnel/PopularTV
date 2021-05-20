package com.example.notes.populartv.screens.main_fragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.notes.populartv.api.TvPost
import com.example.notes.populartv.repository.PopularTvRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


private const val PAGE_SIZE = 20

class MainFragmentViewModel (
    private val repository: PopularTvRepository
) : ViewModel() {

    @ExperimentalPagingApi
    fun getTvPosts(): Flow<PagingData<TvPost>> {
        return repository.getTvPostsFromRemoteMediator().cachedIn(viewModelScope)
    }
}