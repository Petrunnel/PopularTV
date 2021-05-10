package com.example.notes.populartv.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.api.TvPost
import com.example.notes.populartv.utilits.API_KEY

class PopularTvPagingSource(val api: PopularTvApi): PagingSource<Int, TvPost>() {
    override fun getRefreshKey(state: PagingState<Int, TvPost>): Int? {

        return state.anchorPosition

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvPost> {
        return try {
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = api.getTop(API_KEY, nextPage)

            var nextPageNumber: Int? = null
            if(response?.page < response?.total_pages ) {
                nextPageNumber = nextPage + 1
            }
            var prevPageNumber: Int? = null
            if(response?.page != FIRST_PAGE_INDEX) {
                prevPageNumber = nextPage - 1
            }

            LoadResult.Page(data = response.result,
                    prevKey = prevPageNumber,
                    nextKey = nextPageNumber)
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }



}