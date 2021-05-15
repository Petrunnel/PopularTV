package com.example.notes.populartv.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.api.TvPost
import com.example.notes.populartv.utilits.API_KEY

const val FIRST_PAGE_INDEX = 1

@Suppress("UNREACHABLE_CODE")
class PopularTvPagingSource(val api: PopularTvApi): PagingSource<Int, TvPost>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvPost> {
        return try {
            val currentPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = api.getTop(API_KEY, currentPage)

            var nextPageNumber: Int? = null
            if (response.results.isNotEmpty()) {
                nextPageNumber = currentPage + 1
            }
            var prevPageNumber: Int? = null
            if (response.page != FIRST_PAGE_INDEX) {
                prevPageNumber = currentPage - 1
            }

            LoadResult.Page(
                data = response.results,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvPost>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }
}